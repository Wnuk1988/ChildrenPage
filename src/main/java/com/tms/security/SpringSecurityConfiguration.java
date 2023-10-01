package com.tms.security;

import com.tms.security.filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(HttpMethod.POST, "/authentication").permitAll()
                                .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/update").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(RegexRequestMatcher.regexMatcher("/email/[A-Za-z0-9]+")).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(RegexRequestMatcher.regexMatcher("/activate/[A-Za-z0-9]+")).hasAnyRole("ADMIN", "USER")
                                .requestMatchers(RegexRequestMatcher.regexMatcher("/user/[0-9]+")).hasAnyRole("USER","ADMIN")
                                .requestMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/user/favorites").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.POST, "/user/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/user/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers(HttpMethod.DELETE, "/user/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.GET, "/file/**").hasAnyRole("ADMIN","USER")
                                .requestMatchers(HttpMethod.POST, "/file/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/file/**").hasAnyRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/file/**").hasAnyRole("ADMIN")
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

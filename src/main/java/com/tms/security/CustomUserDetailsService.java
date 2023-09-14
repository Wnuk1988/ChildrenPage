package com.tms.security;

import com.tms.exception.SecurityCredentialsUnauthorizedException;
import com.tms.security.domain.SecurityCredentials;
import com.tms.security.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityCredentialsRepository securityCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws SecurityCredentialsUnauthorizedException {
        Optional<SecurityCredentials> securityCredentials = securityCredentialsRepository.findByUserLogin(username);
        if (securityCredentials.isEmpty()) {
            throw new SecurityCredentialsUnauthorizedException();
        }
        return User
                .withUsername(securityCredentials.get().getUserLogin())
                .password(securityCredentials.get().getUserPassword())
                .roles(securityCredentials.get().getUserRole().toString())
                .build();
    }
}

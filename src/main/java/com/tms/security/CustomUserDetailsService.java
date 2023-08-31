package com.tms.security;

import com.tms.exception.SecurityCredentialsUnauthorizedException;
import com.tms.models.SecurityCredentials;
import com.tms.repository.SecurityCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityCredentialsRepository securityCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws SecurityCredentialsUnauthorizedException {
        SecurityCredentials securityCredentials = securityCredentialsRepository.findByUserLogin(username);
        if (securityCredentials == null) {
            throw new SecurityCredentialsUnauthorizedException();
        }
        return User
                .withUsername(securityCredentials.getUserLogin())
                .password(securityCredentials.getUserPassword())
                .roles(securityCredentials.getUserRole().toString())
                .build();
    }
}

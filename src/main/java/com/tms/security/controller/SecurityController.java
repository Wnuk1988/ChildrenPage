package com.tms.security.controller;

import com.tms.security.domain.AuthRequest;
import com.tms.security.domain.AuthResponse;
import com.tms.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        String token = securityService.generateToken(authRequest);
        if (token.isBlank()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}

package com.tms.security.controller;

import com.tms.security.domain.AuthRequest;
import com.tms.security.domain.AuthResponse;
import com.tms.security.domain.RegistrationDTO;
import com.tms.security.domain.SecurityChangeRequest;
import com.tms.security.service.SecurityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @Operation(summary = "User registration", description = "We are registering a user, we need to provide json RegistrationDTO for login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "We have successfully created a user"),
            @ApiResponse(responseCode = "409", description = "Failed to create user"),
            @ApiResponse(responseCode = "400", description = "Client side error"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationDTO registrationDTO) {
        securityService.registration(registrationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "User authentication", description = "We check user authentication, we need to pass json AuthRequest to the input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication was successful"),
            @ApiResponse(responseCode = "401", description = "Failed to authenticate user"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @PostMapping("/authentication")
    public ResponseEntity<AuthResponse> generateToken(@RequestBody AuthRequest authRequest) {
        String token = securityService.generateToken(authRequest);
        if (token.isBlank()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @Operation(summary = "Changing Security data", description = "We are changing the user's security, we need to pass the json securityChangeRequest to the input")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Your request has been successfully processed"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateSecurityCredentials(@RequestBody SecurityChangeRequest securityChangeRequest) {
        securityService.updateSecurityCredentials(securityChangeRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Mail activation", description = "We activate the mail, you need to send an email to the entrance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activation was successful"),
            @ApiResponse(responseCode = "403", description = "Restriction on access to the specified resource"),
            @ApiResponse(responseCode = "400", description = "Client side error"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/email/{email}")
    public ResponseEntity<HttpStatus> activationEmail(@PathVariable String email) {
        securityService.activationEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Getting the activation code", description = "We receive the activation code, we need to provide the code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "We have successfully received the activation code"),
            @ApiResponse(responseCode = "409", description = "Failed to receive activation code"),
            @ApiResponse(responseCode = "500", description = "Server error"),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/activate/{code}")
    public ResponseEntity<HttpStatus> activationCode(@PathVariable String code) {
        boolean isActivated = securityService.activationCode(code);
        if (isActivated) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}


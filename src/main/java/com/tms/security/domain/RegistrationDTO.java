package com.tms.security.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "User registration (DTO)")
@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    @Email
    private String email;
    @NotBlank
    private String userLogin;
    @NotBlank
    private String userPassword;
}

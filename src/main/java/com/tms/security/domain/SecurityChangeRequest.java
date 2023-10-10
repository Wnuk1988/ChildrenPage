package com.tms.security.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Schema(description = "Changing Security data")
@Data
public class SecurityChangeRequest {
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}

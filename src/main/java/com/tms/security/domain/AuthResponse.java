package com.tms.security.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "To receive a token")
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}

package com.tms.security.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "For user authentication")
@Data
public class AuthRequest {
    private String login;
    private String password;
}

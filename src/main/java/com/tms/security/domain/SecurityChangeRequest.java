package com.tms.security.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Changing Security data")
@Data
public class SecurityChangeRequest {
    private String email;
    private String login;
    private String password;
}

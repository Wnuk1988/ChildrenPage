package com.tms.security.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Регистрация пользователя (DTO)")
@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String userLogin;
    private String userPassword;
}

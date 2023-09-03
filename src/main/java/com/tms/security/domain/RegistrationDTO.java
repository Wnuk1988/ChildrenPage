package com.tms.security.domain;

import lombok.Data;

@Data
public class RegistrationDTO {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String userLogin;
    private String userPassword;
}

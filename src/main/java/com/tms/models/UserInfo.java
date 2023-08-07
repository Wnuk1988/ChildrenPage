package com.tms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
}

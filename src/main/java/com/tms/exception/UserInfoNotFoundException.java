package com.tms.exception;

public class UserInfoNotFoundException extends  RuntimeException{
    public UserInfoNotFoundException() {
        super("User not found!");
    }
}

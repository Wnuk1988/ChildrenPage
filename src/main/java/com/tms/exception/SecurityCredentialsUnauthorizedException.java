package com.tms.exception;

public class SecurityCredentialsUnauthorizedException extends RuntimeException{
    public SecurityCredentialsUnauthorizedException(){
        super ("Missing valid credentials!");
    }
}

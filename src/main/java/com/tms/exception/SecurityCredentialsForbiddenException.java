package com.tms.exception;

public class SecurityCredentialsForbiddenException extends RuntimeException{
    public SecurityCredentialsForbiddenException() {
        super("Limited or no access to data!");
    }
}

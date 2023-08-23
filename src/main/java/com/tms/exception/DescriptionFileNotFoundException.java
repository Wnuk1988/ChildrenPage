package com.tms.exception;

public class DescriptionFileNotFoundException extends RuntimeException {
    public DescriptionFileNotFoundException() {
        super("File not found!");
    }
}

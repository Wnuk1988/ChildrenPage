package com.tms.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionResolver {
    @ExceptionHandler(value = SecurityCredentialsForbiddenException.class)
    public ResponseEntity<HttpStatus> securityCredentialsForbiddenException() {
        log.info("Limited or no access to data!");
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = SecurityCredentialsUnauthorizedException.class)
    public ResponseEntity<HttpStatus> securityCredentialsUnauthorized() {
        log.info("Missing valid credentials!");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = DescriptionFileNotFoundException.class)
    public ResponseEntity<HttpStatus> descriptionFileNotFoundException() {
        log.info("FileNotFound exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserInfoNotFoundException.class)
    public ResponseEntity<HttpStatus> userNotFoundException() {
        log.info("UserNotFound exception!");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<HttpStatus> illegalArgumentException() {
        log.info("IllegalArgumentException exception!");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockingFailureException.class)
    public ResponseEntity<HttpStatus> optimisticLockingFailureException() {
        log.info("OptimisticLockingFailureException exception!");
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

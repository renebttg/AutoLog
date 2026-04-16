package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}



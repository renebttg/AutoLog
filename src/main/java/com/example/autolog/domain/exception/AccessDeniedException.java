package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}

package com.example.autolog.exceptions;

/**
 * @author Rene
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}

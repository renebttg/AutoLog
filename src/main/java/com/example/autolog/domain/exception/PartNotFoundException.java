package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException(String message) {
        super(message);
    }
}

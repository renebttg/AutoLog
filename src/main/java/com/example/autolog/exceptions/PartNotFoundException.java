package com.example.autolog.exceptions;

/**
 * @author Rene
 */
public class PartNotFoundException extends RuntimeException {
    public PartNotFoundException(String message) {
        super(message);
    }
}

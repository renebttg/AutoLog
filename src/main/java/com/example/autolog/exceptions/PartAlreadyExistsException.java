package com.example.autolog.exceptions;

/**
 * @author Rene
 */
public class PartAlreadyExistsException extends RuntimeException {
    public PartAlreadyExistsException(String message) {
        super(message);
    }
}

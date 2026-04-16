package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class PartAlreadyExistsException extends RuntimeException {
    public PartAlreadyExistsException(String message) {
        super(message);
    }
}

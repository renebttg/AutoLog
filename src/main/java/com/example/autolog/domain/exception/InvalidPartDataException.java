package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class InvalidPartDataException extends RuntimeException {
    public InvalidPartDataException(String message) {
        super(message);
    }
}

package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class InvalidOperationException extends RuntimeException {

    public InvalidOperationException(String message) {
        super(message);
    }
}

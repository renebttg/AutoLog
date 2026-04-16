package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class TimeoutException extends RuntimeException {

    public TimeoutException(String message) {
        super(message);
    }
}

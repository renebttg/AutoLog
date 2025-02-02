package com.example.autolog.exceptions;

/**
 * @author Rene
 */
public class TimeoutException extends RuntimeException {

    public TimeoutException(String message) {
        super(message);
    }
}

package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}

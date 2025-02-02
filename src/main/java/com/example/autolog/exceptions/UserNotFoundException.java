package com.example.autolog.exceptions;

/**
 * @author Rene
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

}

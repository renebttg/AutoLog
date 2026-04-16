package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String message) {
        super(message);
    }
}

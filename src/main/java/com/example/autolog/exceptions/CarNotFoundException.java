package com.example.autolog.exceptions;

/**
 * @author Rene
 */
public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String message) {
        super(message);
    }
}

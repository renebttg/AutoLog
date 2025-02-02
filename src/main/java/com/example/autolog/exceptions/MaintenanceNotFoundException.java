package com.example.autolog.exceptions;

/**
 * @author Rene
 */
public class MaintenanceNotFoundException extends RuntimeException {
    public MaintenanceNotFoundException(String message) {
        super(message);
    }
}

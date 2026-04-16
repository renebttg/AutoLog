package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class MaintenanceNotFoundException extends RuntimeException {
    public MaintenanceNotFoundException(String message) {
        super(message);
    }
}

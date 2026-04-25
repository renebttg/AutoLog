package com.example.autolog.domain.exception;

/**
 * @author Rene
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

package com.example.autolog.handlers;

import com.example.autolog.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rene
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1 - Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        String message = "Validation failed for some fields. Check the details.";
        Map<String, Object> response = createErrorResponse(message, HttpStatus.BAD_REQUEST);
        response.put("fieldErrors", fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse("Validation failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // 2 - User Not Found Exception
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse("User not found: " + ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    // 3 - Car Not Found Exception
    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCarNotFound(CarNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse("Car not found: " + ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    // 4 - Maintenance Not Found Exception
    @ExceptionHandler(MaintenanceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMaintenanceNotFound(MaintenanceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse("Maintenance record not found: " + ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    // 5 - Entity Not Found Exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse("The requested resource could not be found: " + ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    // 6 - Invalid Operation
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidOperation(InvalidOperationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse("Invalid operation: " + ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // 7 - Authentication Error (401)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationError(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(createErrorResponse("Authentication failed: No valid JWT token provided or the token may have expired.", HttpStatus.UNAUTHORIZED));
    }

    // 8 - Authorization Error (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(createErrorResponse("Authorization error: You don't have the necessary permissions.", HttpStatus.FORBIDDEN));
    }

    // 9 - Data Integrity Violation (409)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(createErrorResponse("Conflict: Data integrity violation - " + ex.getMessage(), HttpStatus.CONFLICT));
    }

    // 10 - External Service Error (503)
    @ExceptionHandler({RestClientException.class, HttpClientErrorException.class})
    public ResponseEntity<Map<String, Object>> handleRestClientError(Exception ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createErrorResponse("External service error: " + ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE));
    }

    // 11 - Runtime Error (500)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeError(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    // 12 - Timeout Error (408)
    @ExceptionHandler(TimeoutException.class)
    public ResponseEntity<Map<String, Object>> handleTimeout(TimeoutException ex) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(createErrorResponse("Request timeout: " + ex.getMessage(), HttpStatus.REQUEST_TIMEOUT));
    }

    // 13 - Invalid Payload (400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPayload(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse("Invalid payload: " + ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // 14 - Part Not Found Exception
    @ExceptionHandler(PartNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePartNotFoundException(PartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createErrorResponse("Part not found: " + ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    // 15 - Part Already Exists Exception
    @ExceptionHandler(PartAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handlePartAlreadyExistsException(PartAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse("Part already exists: " + ex.getMessage(), HttpStatus.BAD_REQUEST));
    }

    // 16 - Invalid Part Data Exception
    @ExceptionHandler(InvalidPartDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPartDataException(InvalidPartDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse("Invalid part data: " + ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
    
    // 17 - General Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(createErrorResponse("Unexpected error: An error occurred while processing your request. Please try again later or contact support.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private Map<String, Object> createErrorResponse(String message, HttpStatus status) {
        return Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message
        );
    }
}

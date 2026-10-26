package com.hgo_soft.device_for_all.common.exceptions.handlers;

import com.hgo_soft.device_for_all.common.api.errors.ApiError;
import com.hgo_soft.device_for_all.common.api.errors.ValidationApiError;
import com.hgo_soft.device_for_all.common.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError apiError = ApiError.build(HttpStatus.NOT_FOUND, "Resource Not Found!!!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ValidationApiError validationApiError = ValidationApiError.build(
                HttpStatus.BAD_REQUEST,
                "Validation Error!!!",
                request.getRequestURI(),
                ex.getFieldErrors()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationApiError);
    }
}

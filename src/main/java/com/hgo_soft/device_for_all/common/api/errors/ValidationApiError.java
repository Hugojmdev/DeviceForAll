package com.hgo_soft.device_for_all.common.api.errors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ValidationApiError(
        LocalDateTime timestamp,
        HttpStatus status,
        Integer code,
        String message,
        String path,
        Map<String, List<String>> fieldErrors
) {
    public static ValidationApiError build(HttpStatus status, String message, String path, List<FieldError> fieldErrors) {
        Map<String, List<String>> fieldValidationErrors = fieldErrors.stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
        return new ValidationApiError(
                LocalDateTime.now(),
                status,
                status.value(),
                message,
                path,
                fieldValidationErrors
        );
    }
}

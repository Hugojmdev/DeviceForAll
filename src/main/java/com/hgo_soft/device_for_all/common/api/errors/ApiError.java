package com.hgo_soft.device_for_all.common.api.errors;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(
        LocalDateTime timestamp,
        HttpStatus status,
        Integer code,
        String message,
        String path
) {
    public static ApiError build(
            HttpStatus status,
            String message,
            String path) {

        return new ApiError(
                LocalDateTime.now(),
                status,
                status.value(),
                message,
                path
        );
    }

}

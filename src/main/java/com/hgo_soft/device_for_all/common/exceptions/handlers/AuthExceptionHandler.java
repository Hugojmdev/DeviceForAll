package com.hgo_soft.device_for_all.common.exceptions.handlers;

import com.hgo_soft.device_for_all.common.api.errors.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex,  HttpServletRequest request) {
        ApiError error = ApiError.build(HttpStatus.UNAUTHORIZED,"Invalid Credentials!!!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ApiError> handleAuthenticationServiceException(AuthenticationServiceException ex,  HttpServletRequest request) {
        ApiError error = ApiError.build(HttpStatus.SERVICE_UNAVAILABLE,"Authentication Unavailable!!!", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleInvalidAuthentication(AuthenticationException ex, HttpServletRequest request) {
        ApiError error = ApiError.build(HttpStatus.UNAUTHORIZED,"Invalid Credentials!!!", request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

}

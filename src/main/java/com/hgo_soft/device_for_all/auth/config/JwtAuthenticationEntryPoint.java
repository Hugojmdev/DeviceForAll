package com.hgo_soft.device_for_all.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgo_soft.device_for_all.common.api.errors.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authenticationException) throws IOException {
        sendUnauthorized(request, response, "Authentication required!!!");
    }

    public void sendUnauthorized(
            HttpServletRequest request,
            HttpServletResponse response,
            String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"invalid_token\"");

        ApiError apiError = ApiError.build(
                HttpStatus.UNAUTHORIZED,
                message,
                request.getRequestURI()
        );

        objectMapper.writeValue(response.getOutputStream(), apiError);
    }
}

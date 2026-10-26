package com.hgo_soft.device_for_all.auth.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper().findAndRegisterModules();
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);
    }

    @Test
    public void sendUnauthorized_returnStructuredJsonResponse() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/devices");

        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthenticationEntryPoint.sendUnauthorized(request, response, "Invalid Token!!!");

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertNotNull(response.getContentType());
        MediaType actualType = MediaType.parseMediaType(response.getContentType());
        assertTrue(actualType.isCompatibleWith(MediaType.APPLICATION_JSON));
        assertEquals(StandardCharsets.UTF_8.name(), response.getCharacterEncoding());
        assertEquals("Bearer error=\"invalid_token\"", response.getHeader(HttpHeaders.WWW_AUTHENTICATE));

        JsonNode body = objectMapper.readTree(response.getContentAsString());
        assertEquals("UNAUTHORIZED", body.get("status").asText());
        assertEquals(401, body.get("code").asInt());
        assertEquals("Invalid Token!!!", body.get("message").asText());
        assertEquals("/api/devices", body.get("path").asText());
    }
}

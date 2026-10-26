package com.hgo_soft.device_for_all.common.exceptions.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JsonTest
@Import(JwtAccessDeniedHandler.class)
class JwtAccessDeniedHandlerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;

    @Test
    void handle_WhenUserLacksPermission_ReturnsForbiddenApiError() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/devices");
        MockHttpServletResponse response = new MockHttpServletResponse();

        accessDeniedHandler.handle(
                request,
                response,
                new AccessDeniedException("Access Denied")
        );

        JsonNode body = objectMapper.readTree(response.getContentAsByteArray());

        assertEquals(403, response.getStatus());
        assertTrue(MediaType.APPLICATION_JSON.isCompatibleWith(
                MediaType.parseMediaType(response.getContentType())
        ));
        assertEquals("FORBIDDEN", body.get("status").asText());
        assertEquals(403, body.get("code").asInt());
        assertEquals("Access denied!!!", body.get("message").asText());
        assertEquals("/api/devices", body.get("path").asText());
        assertDoesNotThrow(() -> LocalDateTime.parse(body.get("timestamp").asText()));
    }

    @Test
    void configuredObjectMapper_SerializesLocalDateTime() {
        LocalDateTime timestamp = LocalDateTime.of(2026, 10, 23, 14, 30);

        String json = assertDoesNotThrow(() -> objectMapper.writeValueAsString(timestamp));

        assertEquals("\"2026-10-23T14:30:00\"", json);
    }
}

package com.hgo_soft.device_for_all.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgo_soft.device_for_all.auth.dtos.LoginRequest;
import com.hgo_soft.device_for_all.users.dtos.CreateUserRequest;
import com.hgo_soft.device_for_all.users.dtos.UserResponse;
import com.hgo_soft.device_for_all.users.entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SensitiveDataExposureTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUserRequest_ShouldAcceptButNotExposePassword() throws Exception {
        String rawPassword = "validPassword123";
        String requestJson = """
                {
                  "username": "test-user",
                  "email": "test@example.com",
                  "password": "validPassword123"
                }
                """;

        CreateUserRequest request = objectMapper.readValue(
                requestJson,
                CreateUserRequest.class
        );
        String responseJson = objectMapper.writeValueAsString(request);

        assertEquals(rawPassword, request.password());
        assertFalse(request.toString().contains(rawPassword));
        assertFalse(responseJson.contains(rawPassword));
        assertFalse(responseJson.contains("password"));
    }

    @Test
    void loginRequest_ShouldAcceptButNotExposePassword() throws Exception {
        String rawPassword = "validPassword123";
        String requestJson = """
                {
                  "username": "test-user",
                  "password": "validPassword123"
                }
                """;

        LoginRequest request = objectMapper.readValue(
                requestJson,
                LoginRequest.class
        );
        String responseJson = objectMapper.writeValueAsString(request);

        assertEquals(rawPassword, request.password());
        assertFalse(request.toString().contains(rawPassword));
        assertFalse(responseJson.contains(rawPassword));
        assertFalse(responseJson.contains("password"));
    }

    @Test
    void user_ShouldNotSerializePasswordHash() throws Exception {
        String passwordHash = "$2a$10$example-password-hash";
        User user = User.builder()
                .id(1L)
                .username("test-user")
                .email("test@example.com")
                .passwordHash(passwordHash)
                .enabled(true)
                .build();

        String json = objectMapper.writeValueAsString(user);

        assertFalse(json.contains(passwordHash));
        assertFalse(json.contains("passwordHash"));
    }

    @Test
    void userResponse_ShouldNotContainPasswordOrHash() throws Exception {
        UserResponse response = new UserResponse(
                1L,
                "test-user",
                "test@example.com",
                true
        );

        String json = objectMapper.writeValueAsString(response);

        assertFalse(json.contains("password"));
        assertFalse(json.contains("passwordHash"));
    }
}

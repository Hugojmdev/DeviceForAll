package com.hgo_soft.device_for_all.auth.controllers;

import com.hgo_soft.device_for_all.auth.services.JwtService;
import com.hgo_soft.device_for_all.common.exceptions.handlers.AuthExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private AuthenticationManager authenticationManager;
    private StubJwtService jwtService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtService = new StubJwtService();

        AuthController controller = new AuthController(authenticationManager, jwtService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new AuthExceptionHandler())
                .build();
    }

    @Test
    void login_WhenPasswordIsIncorrect_ReturnsUnauthorized() throws Exception {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest("alice", "incorrect-password")))
                .andExpect(status().isUnauthorized());

        assertEquals(0, jwtService.generationCount);
    }

    @Test
    void login_WhenUsernameIsUnknown_ReturnsUnauthorized() throws Exception {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest("missing-user", "any-password")))
                .andExpect(status().isUnauthorized());

        assertEquals(0, jwtService.generationCount);
    }

    @Test
    void login_WhenAuthenticationFails_ReturnsApiError() throws Exception {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest("alice", "incorrect-password")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value("UNAUTHORIZED"))
                .andExpect(jsonPath("$.code").value(401))
                .andExpect(jsonPath("$.message").value("Invalid Credentials!!!"))
                .andExpect(jsonPath("$.path").value("/auth/login"));
    }

    @Test
    void login_WhenCredentialsAreValid_ReturnsToken() throws Exception {
        Authentication authenticated = UsernamePasswordAuthenticationToken.authenticated(
                "alice",
                null,
                List.of()
        );
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authenticated);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest("alice", "correct-password")))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("signed-jwt-token"));

        assertEquals(1, jwtService.generationCount);
        assertEquals("alice", jwtService.lastUsername);
    }

    @Test
    void login_whenUserIsDisabled_ReturnsApiError() throws Exception {
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new DisabledException("User is disabled!!!"));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest("disabled-user", "disabled-password")))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Invalid Credentials!!!"));

        assertEquals(0, jwtService.generationCount);
    }

    private String loginRequest(String username, String password) {
        return """
                {
                  "username": "%s",
                  "password": "%s"
                }
                """.formatted(username, password);
    }

    private static final class StubJwtService extends JwtService {

        private int generationCount;
        private String lastUsername;

        @Override
        public String generateJwtToken(String username) {
            generationCount++;
            lastUsername = username;
            return "signed-jwt-token";
        }
    }
}

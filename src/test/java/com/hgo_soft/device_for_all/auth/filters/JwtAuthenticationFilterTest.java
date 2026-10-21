package com.hgo_soft.device_for_all.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgo_soft.device_for_all.auth.config.JwtAuthenticationEntryPoint;
import com.hgo_soft.device_for_all.auth.services.JwtService;
import com.hgo_soft.device_for_all.auth.services.SecurityUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    private JwtService jwtService;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private SecurityUserDetailsService securityUserDetailsService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        jwtService = mock(JwtService.class);
        securityUserDetailsService = mock(SecurityUserDetailsService.class);
        objectMapper = new ObjectMapper().findAndRegisterModules();
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint(objectMapper);

        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, securityUserDetailsService, jwtAuthenticationEntryPoint);

        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void teardown() {
        SecurityContextHolder.clearContext();
    }

    private MockHttpServletRequest protectedRequest() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/devices");
        request.setServletPath("/api/devices");
        return request;
    }

    @Test
    void malformedToken_returnUnauthorized() throws Exception {
        MockHttpServletRequest request = protectedRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer malformed-Token");

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        when(jwtService.extractUsername("malformed-Token"))
                .thenThrow(new MalformedJwtException("Malformed Token!!!"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        assertEquals(401, response.getStatus());
        assertEquals("Invalid Token!!!",
                objectMapper.readTree(response.getContentAsString()).get("message").asText());
        verify(jwtService, times(1)).extractUsername("malformed-Token");
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void expiredToken_returnUnauthorizedWithExpiredMessage() throws Exception {
        MockHttpServletRequest request = protectedRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer expired-Token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        when(jwtService.extractUsername("expired-Token"))
                .thenThrow(new ExpiredJwtException(Jwts.header(), Jwts.claims(),"expired-Token"));
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(401, response.getStatus());
        assertEquals("Token has expired!!!",
                objectMapper.readTree(response.getContentAsString()).get("message").asText());

        verify(jwtService, times(1)).extractUsername("expired-Token");
        verify(filterChain, never()).doFilter(any(), any());
    }

    @Test
    void validToken_authenticateUserAndContinuesRequest() throws Exception {
        MockHttpServletRequest request = protectedRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer valid-token");
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = User.withUsername("valid-user")
                .password("valid-password")
                .authorities("valid-role")
                .build();

        when(jwtService.extractUsername("valid-token")).thenReturn("valid-user");
        when(securityUserDetailsService.loadUserByUsername("valid-user")).thenReturn(userDetails);
        when(jwtService.isTokenValid("valid-token", "valid-user")).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("valid-user", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void missingAuthorizationHeader_continuesWithoutAuthentication() throws Exception {
        MockHttpServletRequest request = protectedRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verifyNoMoreInteractions(jwtService);
    }
}

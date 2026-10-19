package com.hgo_soft.device_for_all.auth.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SecurityBeansConfigTest {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private DaoAuthenticationProvider authenticationProvider;

    @BeforeEach
    void setUp() {
        SecurityBeansConfig config = new SecurityBeansConfig();
        userDetailsService = mock(UserDetailsService.class);
        passwordEncoder = config.passwordEncoder();
        authenticationProvider = config.authenticationProvider(userDetailsService, passwordEncoder);
    }

    @Test
    void authenticate_WhenCredentialsAreValid_ReturnsAuthenticatedUser() {
        UserDetails user = new User(
                "alice",
                passwordEncoder.encode("correct-password"),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
        when(userDetailsService.loadUserByUsername("alice")).thenReturn(user);

        Authentication result = authenticationProvider.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(
                        "alice",
                        "correct-password"
                )
        );

        assertTrue(result.isAuthenticated());
        assertEquals("alice", result.getName());
        assertEquals(List.of(new SimpleGrantedAuthority("ROLE_USER")), result.getAuthorities());
        verify(userDetailsService).loadUserByUsername("alice");
    }

    @Test
    void authenticate_WhenPasswordIsIncorrect_ThrowsBadCredentialsException() {
        UserDetails user = User.withUsername("alice")
                .password(passwordEncoder.encode("correct-password"))
                .authorities("ROLE_USER")
                .build();
        when(userDetailsService.loadUserByUsername("alice")).thenReturn(user);

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        "alice",
                        "incorrect-password"
                );

        assertThrows(
                BadCredentialsException.class,
                () -> authenticationProvider.authenticate(authentication)
        );
        verify(userDetailsService).loadUserByUsername("alice");
    }

    @Test
    void authenticate_WhenUsernameDoesNotExist_ThrowsBadCredentialsException() {
        when(userDetailsService.loadUserByUsername("missing-user"))
                .thenThrow(new UsernameNotFoundException("User not found"));

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        "missing-user",
                        "any-password"
                );

        assertThrows(
                BadCredentialsException.class,
                () -> authenticationProvider.authenticate(authentication)
        );
        verify(userDetailsService).loadUserByUsername("missing-user");
    }
}

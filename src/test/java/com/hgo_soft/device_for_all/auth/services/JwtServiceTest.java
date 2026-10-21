package com.hgo_soft.device_for_all.auth.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;
    private SecretKey secretKey;

    @BeforeEach
    void setup() {
        jwtService = new JwtService();
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String enccodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        ReflectionTestUtils.setField(jwtService, "JWT_KEY", enccodedKey);
        jwtService.init();
    }

    @Test
    void validToken_returnValidUsername(){
        String token = jwtService.generateJwtToken("valid-user");
        assertEquals("valid-user", jwtService.extractUsername(token));
        assertTrue(jwtService.isTokenValid(token,"valid-user"));
    }

    @Test
    void malformedToken_throwsMalformerdJwtTokenException(){
        assertThrows(MalformedJwtException.class, ()->jwtService.extractUsername("malformed-token"));
    }

    @Test
    void expiredToken_throwsExpiredJwtTokenException(){
        Instant now = Instant.now();
        String expiredToken = Jwts.builder()
                .setSubject("valid-user")
                .setIssuedAt(Date.from(now.minusSeconds(120)))
                .setExpiration(Date.from(now.minusSeconds(60)))
                .signWith(secretKey)
                .compact();

        assertThrows(ExpiredJwtException.class, ()->jwtService.extractUsername(expiredToken));
    }
}

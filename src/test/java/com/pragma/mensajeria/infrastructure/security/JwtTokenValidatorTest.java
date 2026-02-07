package com.pragma.mensajeria.infrastructure.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenValidatorTest {

    private static final String SECRET = "01234567890123456789012345678901"; // 32 chars

    @Test
    void validToken_extractsClaimsAndIsValid() {
        var key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .subject("user@example.com")
                .claim("role", "CLIENT")
                .claim("userId", 42L)
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(key)
                .compact();

        JwtTokenValidator validator = new JwtTokenValidator(SECRET);

        assertEquals("user@example.com", validator.extractEmail(token));
        assertEquals("CLIENT", validator.extractRole(token));
        assertEquals(42L, validator.extractUserId(token));
        assertTrue(validator.isTokenValid(token));
    }

    @Test
    void expiredToken_returnsNullsAndIsInvalid() {
        var key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .subject("user@example.com")
                .claim("role", "CLIENT")
                .claim("userId", 42L)
                .expiration(new Date(System.currentTimeMillis() - 1_000))
                .signWith(key)
                .compact();

        JwtTokenValidator validator = new JwtTokenValidator(SECRET);

        assertNull(validator.extractEmail(token));
        assertNull(validator.extractRole(token));
        assertNull(validator.extractUserId(token));
        assertFalse(validator.isTokenValid(token));
    }

    @Test
    void malformedToken_returnsNullsAndIsInvalid() {
        JwtTokenValidator validator = new JwtTokenValidator(SECRET);

        String bad = "not-a-token";

        assertNull(validator.extractEmail(bad));
        assertNull(validator.extractRole(bad));
        assertNull(validator.extractUserId(bad));
        assertFalse(validator.isTokenValid(bad));
    }
}

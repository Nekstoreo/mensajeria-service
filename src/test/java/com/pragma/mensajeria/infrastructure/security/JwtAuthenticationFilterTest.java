package com.pragma.mensajeria.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilter_noHeader_callsChainAndNoAuth() throws Exception {
        JwtTokenValidator validator = mock(JwtTokenValidator.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(validator);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNull(auth);
    }

    @Test
    void doFilter_withValidToken_setsAuthentication() throws Exception {
        JwtTokenValidator validator = mock(JwtTokenValidator.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(validator);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String token = "token123";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(validator.isTokenValid(token)).thenReturn(true);
        when(validator.extractEmail(token)).thenReturn("user@example.com");
        when(validator.extractRole(token)).thenReturn("DRIVER");
        when(validator.extractUserId(token)).thenReturn(7L);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);

        var auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(auth);
        assertEquals("user@example.com", auth.getPrincipal());
        assertEquals(7L, auth.getDetails());
        assertTrue(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DRIVER")));
    }

    @Test
    void doFilter_withInvalidToken_doesNotSetAuthentication() throws Exception {
        JwtTokenValidator validator = mock(JwtTokenValidator.class);
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(validator);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String token = "token123";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);

        when(validator.isTokenValid(token)).thenReturn(false);

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}

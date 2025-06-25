package com.miempresa.ecommerce.security;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Tests unitarios para JwtAuthenticationFilter
 * 
 */
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
        
        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities(new ArrayList<>())
                .build();
    }

    @Test
    void testDoFilterInternal_WithValidToken_ShouldSetAuthentication() throws ServletException, IOException {
        // Dado
        String token = "valid.jwt.token";
        String bearerToken = "Bearer " + token;
        
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getName());
        
        verify(filterChain).doFilter(request, response);
        verify(jwtUtil).validateToken(token);
        verify(jwtUtil).getUsernameFromToken(token);
        verify(userDetailsService).loadUserByUsername("testuser");
    }

    @Test
    void testDoFilterInternal_WithInvalidToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        // Dado
        String token = "invalid.jwt.token";
        String bearerToken = "Bearer " + token;
        
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtUtil.validateToken(token)).thenReturn(false);

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        
        verify(filterChain).doFilter(request, response);
        verify(jwtUtil).validateToken(token);
        verify(jwtUtil, never()).getUsernameFromToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void testDoFilterInternal_WithNoAuthorizationHeader_ShouldNotSetAuthentication() throws ServletException, IOException {
        // Dado
        when(request.getHeader("Authorization")).thenReturn(null);

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        
        verify(filterChain).doFilter(request, response);
        verify(jwtUtil, never()).validateToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void testDoFilterInternal_WithInvalidBearerFormat_ShouldNotSetAuthentication() throws ServletException, IOException {
        // Dado
        when(request.getHeader("Authorization")).thenReturn("InvalidFormat token");

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        
        verify(filterChain).doFilter(request, response);
        verify(jwtUtil, never()).validateToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void testDoFilterInternal_WithEmptyToken_ShouldNotSetAuthentication() throws ServletException, IOException {
        // Dado
        when(request.getHeader("Authorization")).thenReturn("Bearer ");

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        
        verify(filterChain).doFilter(request, response);
        verify(jwtUtil, never()).validateToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void testDoFilterInternal_WithExceptionInTokenProcessing_ShouldContinueFilterChain() throws ServletException, IOException {
        // Dado
        String token = "valid.jwt.token";
        String bearerToken = "Bearer " + token;
        
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtUtil.validateToken(token)).thenThrow(new RuntimeException("Token processing error"));

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithUserDetailsServiceException_ShouldContinueFilterChain() throws ServletException, IOException {
        // Dado
        String token = "valid.jwt.token";
        String bearerToken = "Bearer " + token;
        
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser"))
                .thenThrow(new RuntimeException("User not found"));

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithAlreadyAuthenticatedUser_ShouldOverrideAuthentication() throws ServletException, IOException {
        // Dado
        String token = "valid.jwt.token";
        String bearerToken = "Bearer " + token;
        
        // Set existing authentication
        SecurityContextHolder.getContext().setAuthentication(
                mock(org.springframework.security.core.Authentication.class));
        
        when(request.getHeader("Authorization")).thenReturn(bearerToken);
        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        // Cuando
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Entonces
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("testuser", SecurityContextHolder.getContext().getAuthentication().getName());
        verify(filterChain).doFilter(request, response);
    }
}

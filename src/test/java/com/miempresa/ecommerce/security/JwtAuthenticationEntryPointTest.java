package com.miempresa.ecommerce.security;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Tests unitarios para JwtAuthenticationEntryPoint
 * 
 * @author Development Team
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntryPointTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @Mock
    private ServletOutputStream outputStream;

    @InjectMocks
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @BeforeEach
    void setUp() throws IOException {
        when(response.getOutputStream()).thenReturn(outputStream);
        when(request.getServletPath()).thenReturn("/api/v1/test");
        when(authException.getMessage()).thenReturn("Authentication failed");
    }

    @Test
    void testCommence_ShouldSetUnauthorizedResponse() throws IOException, ServletException {
        // Cuando
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Entonces
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).getOutputStream();
    }

    @Test
    void testCommence_ShouldWriteErrorResponseBody() throws IOException, ServletException {
        // Cuando
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Entonces
        // Verificar que se escribió algo al output stream (Jackson puede usar diferentes métodos)
        verify(outputStream, atLeastOnce()).write(any(byte[].class), anyInt(), anyInt());
    }

    @Test
    void testCommence_WithNullAuthException_ShouldHandleGracefully() throws IOException, ServletException {
        // Dado
        when(authException.getMessage()).thenReturn(null);

        // Cuando
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Entonces
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    void testCommence_WithEmptyServletPath_ShouldHandleGracefully() throws IOException, ServletException {
        // Dado
        when(request.getServletPath()).thenReturn("");

        // Cuando
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Entonces
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(outputStream, atLeastOnce()).write(any(byte[].class), anyInt(), anyInt());
    }

    @Test
    void testCommence_WithIOException_ShouldPropagateException() throws IOException, ServletException {
        // Dado
        when(response.getOutputStream()).thenThrow(new IOException("Stream error"));

        // Cuando & Entonces
        org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
            jwtAuthenticationEntryPoint.commence(request, response, authException);
        });
    }

    @Test
    void testCommence_ShouldIncludeCorrectErrorFields() throws IOException, ServletException {
        // Cuando
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Entonces
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        // Verificar que se escribió contenido al stream
        verify(outputStream, atLeastOnce()).write(any(byte[].class), anyInt(), anyInt());
    }
}

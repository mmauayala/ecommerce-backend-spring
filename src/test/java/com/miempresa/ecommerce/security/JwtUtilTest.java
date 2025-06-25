package com.miempresa.ecommerce.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Tests unitarios para JwtUtil
 * 
 */
@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private UserDetails userDetails;
    private String validToken;
    private String expiredToken;

    @BeforeEach
    void setUp() {
        // Configurar propiedades de prueba
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "test-secret-key-for-jwt-token-generation-minimum-256-bits-required");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 86400000); // 24 horas
        ReflectionTestUtils.setField(jwtUtil, "jwtIssuer", "ecommerce-test");

        // Crear UserDetails de prueba
        userDetails = User.builder()
                .username("testuser")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // Generar token válido
        validToken = jwtUtil.generateToken(userDetails);

        // Configurar token expirado para pruebas
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", -1000); // Token expirado
        expiredToken = jwtUtil.generateToken(userDetails);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 86400000); // Restaurar valor
    }

    @Test
    void testGenerateToken_WithUserDetails_ShouldReturnValidToken() {
        // Cuando
        String token = jwtUtil.generateToken(userDetails);

        // Entonces
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.startsWith("eyJ")); // JWT tokens start with eyJ
    }

    @Test
    void testGenerateToken_WithExtraClaims_ShouldReturnValidToken() {
        // Dado
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", "ADMIN");
        extraClaims.put("department", "IT");

        // Cuando
        String token = jwtUtil.generateToken(extraClaims, "testuser");

        // Entonces
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void testGetUsernameFromToken_WithValidToken_ShouldReturnUsername() {
        // Cuando
        String username = jwtUtil.getUsernameFromToken(validToken);

        // Entonces
        assertEquals("testuser", username);
    }

    @Test
    void testGetExpirationDateFromToken_WithValidToken_ShouldReturnFutureDate() {
        // Cuando
        Date expirationDate = jwtUtil.getExpirationDateFromToken(validToken);

        // Entonces
        assertNotNull(expirationDate);
        assertTrue(expirationDate.after(new Date()));
    }

    @Test
    void testValidateToken_WithValidTokenAndUserDetails_ShouldReturnTrue() {
        // Cuando
        Boolean isValid = jwtUtil.validateToken(validToken, userDetails);

        // Entonces
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_WithValidTokenOnly_ShouldReturnTrue() {
        // Cuando
        Boolean isValid = jwtUtil.validateToken(validToken);

        // Entonces
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_WithExpiredToken_ShouldReturnFalse() {
        // Cuando
        Boolean isValid = jwtUtil.validateToken(expiredToken);

        // Entonces
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_WithMalformedToken_ShouldReturnFalse() {
        // Dado
        String malformedToken = "invalid.jwt.token";

        // Cuando
        Boolean isValid = jwtUtil.validateToken(malformedToken);

        // Entonces
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_WithNullToken_ShouldReturnFalse() {
        // Cuando
        Boolean isValid = jwtUtil.validateToken(null);

        // Entonces
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_WithEmptyToken_ShouldReturnFalse() {
        // Cuando
        Boolean isValid = jwtUtil.validateToken("");

        // Entonces
        assertFalse(isValid);
    }

    @Test
    void testValidateToken_WithWrongUsername_ShouldReturnFalse() {
        // Dado
        UserDetails wrongUserDetails = User.builder()
                .username("wronguser")
                .password("password")
                .authorities(new ArrayList<>())
                .build();

        // Cuando
        Boolean isValid = jwtUtil.validateToken(validToken, wrongUserDetails);

        // Entonces
        assertFalse(isValid);
    }

    @Test
    void testGetClaimFromToken_WithValidToken_ShouldReturnClaim() {
        // Cuando
        String subject = jwtUtil.getClaimFromToken(validToken, claims -> claims.getSubject());

        // Entonces
        assertEquals("testuser", subject);
    }

    @Test
    void testTokenExpiration_ShouldBeInFuture() {
        // Dado
        String token = jwtUtil.generateToken(userDetails);

        // Cuando
        Date expirationDate = jwtUtil.getExpirationDateFromToken(token);

        // Entonces
        assertTrue(expirationDate.after(new Date()));
    }
}

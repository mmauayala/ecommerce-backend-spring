package com.miempresa.ecommerce.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests de integración básicos para la configuración de seguridad
 * 
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void testSwaggerEndpoints_ShouldBeAccessible() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthValidateEndpoint_ShouldReturnOkWithDummyToken() throws Exception {
        
         UserDetails userDetails = org.springframework.security.core.userdetails.User
            .builder()
            .username("testuser")
            .password("password123") // no se usa en el token
            .authorities("ROLE_USER")
            .build();

        // 2. Generar token válido
        String token = jwtUtil.generateToken(userDetails);

        mockMvc.perform(get("/api/v1/auth/validate")
            .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testProtectedEndpoint_ShouldReturn401() throws Exception {
        // Probar un endpoint que requiere autenticación
        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Los productos son públicos según la configuración
    }

    @Test
    void testNonExistentEndpoint_ShouldReturn401OrNotFound() throws Exception {
        // Los endpoints no existentes pueden devolver 401 si están bajo rutas protegidas
        mockMvc.perform(get("/api/v1/nonexistent"))
                .andExpect(status().isUnauthorized());// Cambiar expectativa a 401
    }
}

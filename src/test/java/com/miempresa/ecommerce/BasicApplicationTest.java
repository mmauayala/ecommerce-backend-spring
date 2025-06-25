package com.miempresa.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test básico para verificar que la aplicación puede arrancar
 * 
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BasicApplicationTest {

    @Test
    void contextLoads() {
        // Este test verifica que el contexto de Spring se puede cargar
        // Si hay problemas de configuración, este test fallará
    }
}

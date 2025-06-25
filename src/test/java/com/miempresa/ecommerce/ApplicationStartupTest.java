package com.miempresa.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test simple para verificar que la aplicación arranca correctamente
 * 
 */
@SpringBootTest(classes = EcommerceApplication.class)
@ActiveProfiles("test")
class ApplicationStartupTest {

    @Test
    void applicationContextLoads() {
        // Este test simplemente verifica que el contexto de Spring se carga sin errores
    }
}

package com.miempresa.ecommerce.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Configuración de OpenAPI/Swagger para documentación de la API
 * 
 * @author Development Team
 * @version 1.0.0
 */
@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "E-commerce API",
        version = "1.0.0",
        description = "API REST para sistema de e-commerce empresarial",
        contact = @Contact(
            name = "Development Team",
            email = "dev@miempresa.com",
            url = "https://miempresa.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(url = "http://localhost:8080", description = "Servidor de Desarrollo"),
        @Server(url = "https://api.miempresa.com", description = "Servidor de Producción")
    }
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    description = "Ingrese el token JWT en el formato: Bearer {token}"
)
public class OpenApiConfig {
}

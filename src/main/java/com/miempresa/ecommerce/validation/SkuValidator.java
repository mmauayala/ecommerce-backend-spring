package com.miempresa.ecommerce.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miempresa.ecommerce.repository.ProductRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para SKU único
 * 
 */
@Component
public class SkuValidator implements ConstraintValidator<ValidSku, String> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void initialize(ValidSku constraintAnnotation) {
        // Inicialización si es necesaria
    }

    @Override
    public boolean isValid(String sku, ConstraintValidatorContext context) {
        if (sku == null || sku.trim().isEmpty()) {
            return true; // Permitir SKU vacío, será manejado por @NotBlank si es requerido
        }

        // Validar formato del SKU (letras, números, guiones)
        if (!sku.matches("^[A-Za-z0-9-]+$")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El SKU solo puede contener letras, números y guiones")
                   .addConstraintViolation();
            return false;
        }

        // Validar longitud
        if (sku.length() < 3 || sku.length() > 50) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El SKU debe tener entre 3 y 50 caracteres")
                   .addConstraintViolation();
            return false;
        }

        // Validar unicidad
        if (productRepository.existsBySku(sku)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El SKU ya existe")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}

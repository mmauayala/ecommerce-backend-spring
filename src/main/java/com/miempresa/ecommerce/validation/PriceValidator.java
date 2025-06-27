package com.miempresa.ecommerce.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para precio
 * 
 */
public class PriceValidator implements ConstraintValidator<ValidPrice, BigDecimal> {

    @Override
    public void initialize(ValidPrice constraintAnnotation) {
        // Inicialización si es necesaria
    }

    @Override
    public boolean isValid(BigDecimal price, ConstraintValidatorContext context) {
        if (price == null) {
            return true; // Permitir null, será manejado por @NotNull si es requerido
        }

        // Validar que sea mayor a 0
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El precio debe ser mayor que 0")
                   .addConstraintViolation();
            return false;
        }

        // Validar máximo 2 decimales
        if (price.scale() > 2) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El precio puede tener un máximo de 2 decimales")
                   .addConstraintViolation();
            return false;
        }

        // Validar valor máximo razonable
        if (price.compareTo(new BigDecimal("999999.99")) > 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El precio no puede exceder 999.999,99")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}

package com.miempresa.ecommerce.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validador para stock
 * 
 */
public class StockValidator implements ConstraintValidator<ValidStock, Integer> {

    @Override
    public void initialize(ValidStock constraintAnnotation) {
        // Inicialización si es necesaria
    }

    @Override
    public boolean isValid(Integer stock, ConstraintValidatorContext context) {
        if (stock == null) {
            return true; // Permitir null, será manejado por @NotNull si es requerido
        }

        // Validar que no sea negativo
        if (stock < 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El stock no puede ser negativo")
                   .addConstraintViolation();
            return false;
        }

        // Validar límite máximo razonable
        if (stock > 1000000) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("El stock no puede exceder de 1.000.000 de unidades")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}

package com.miempresa.ecommerce.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Validación personalizada para stock
 * 
 */
@Documented
@Constraint(validatedBy = StockValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStock {
    String message() default "Las existencias deben ser no negativas y estar dentro de límites razonables";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

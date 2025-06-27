package com.miempresa.ecommerce.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Validación personalizada para SKU único
 * 
 */
@Documented
@Constraint(validatedBy = SkuValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSku {
    String message() default "El SKU debe ser único y seguir el formato correcto";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

package com.miempresa.ecommerce.exception;

/**
 * Excepción para categoría no encontrada
 * 
 */
public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException(String message) {
        super("CATEGORY_NOT_FOUND", message);
    }

    public CategoryNotFoundException(Long categoryId) {
        super("CATEGORY_NOT_FOUND", "Categoría no encontrada con ID: " + categoryId);
    }

    public CategoryNotFoundException(String field, String value) {
        super("CATEGORY_NOT_FOUND", "Categoría no encontrada con " + field + ": " + value);
    }
}

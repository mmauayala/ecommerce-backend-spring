package com.miempresa.ecommerce.exception;

/**
 * Excepción para producto no encontrado
 * 
 */
public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(String message) {
        super("PRODUCT_NOT_FOUND", message);
    }

    public ProductNotFoundException(Long productId) {
        super("PRODUCT_NOT_FOUND", "Producto no encontrado con ID: " + productId);
    }

    public ProductNotFoundException(String field, String value) {
        super("PRODUCT_NOT_FOUND", "Producto no encontrado con " + field + ": " + value);
    }
}

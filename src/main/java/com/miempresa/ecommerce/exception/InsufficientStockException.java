package com.miempresa.ecommerce.exception;

/**
 * Excepción para stock insuficiente
 * 
 */
public class InsufficientStockException extends BusinessException {

    public InsufficientStockException(String message) {
        super("INSUFFICIENT_STOCK", message);
    }

    public InsufficientStockException(Long productId, Integer requested, Integer available) {
        super("INSUFFICIENT_STOCK", 
              String.format("Stock insuficiente para el ID de producto %d. Solicitado: %d, Disponible: %d", 
                          productId, requested, available));
    }

    public InsufficientStockException(String productName, Integer requested, Integer available) {
        super("INSUFFICIENT_STOCK", 
              String.format("Stock insuficiente del producto '%s'. Solicitado: %d, Disponible: %d", 
                          productName, requested, available));
    }
}

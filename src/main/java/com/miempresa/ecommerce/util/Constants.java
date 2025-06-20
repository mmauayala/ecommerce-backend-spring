package com.miempresa.ecommerce.util;

public final class Constants {

    // Constantes de API
    public static final String API_VERSION = "v1";
    public static final String API_BASE_PATH = "/api/" + API_VERSION;
    
    // Constantes de Seguridad
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_HEADER_NAME = "Authorization";
    
    // Constantes de Paginacion 
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_FIELD = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    
    // Constantes de Producto
    public static final int LOW_STOCK_THRESHOLD = 10;
    public static final int MAX_PRODUCT_NAME_LENGTH = 255;
    public static final int MAX_PRODUCT_DESCRIPTION_LENGTH = 1000;
    
    // Constantes de usuario
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_EMAIL_LENGTH = 100;
    
    // Codigos de Error
    public static final String ERROR_PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    public static final String ERROR_USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String ERROR_DUPLICATE_SKU = "DUPLICATE_SKU";
    public static final String ERROR_DUPLICATE_USERNAME = "DUPLICATE_USERNAME";
    public static final String ERROR_DUPLICATE_EMAIL = "DUPLICATE_EMAIL";
    public static final String ERROR_INSUFFICIENT_STOCK = "INSUFFICIENT_STOCK";
    public static final String ERROR_INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
    
    // Constructor privado para evitar la instanciación
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
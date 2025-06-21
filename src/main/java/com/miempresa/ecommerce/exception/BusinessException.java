package com.miempresa.ecommerce.exception;

public class BusinessException extends RuntimeException {

    private String errorCode;

    /**
     * Constructor con mensaje 
     * @param message Mensaje de error 
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa
     * @param message Mensaje de error 
     * @param cause Causa principal
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con Codigo de error y causa
     * @param errorCode Codigo de error
     * @param message Mensaje de error 
     */
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor con Codigo de erro, mensaje y causa
     * @param errorCode Codigo de error
     * @param message Mensaje de error
     * @param cause Causa principal
     */
    public BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

package com.miempresa.ecommerce.service;

import com.miempresa.ecommerce.dto.request.AuthRequestDTO;
import com.miempresa.ecommerce.dto.response.AuthResponseDTO;

/**
 * Interfaz de servicio para la lógica empresarial de autenticación
 * 
 */
public interface IAuthService {

    /**
     * Registrar un nuevo usuario
     * @param authRequest Registration request
     * @return Respuesta de autenticación con token JWT
     */
    AuthResponseDTO register(AuthRequestDTO authRequest);

    /**
     * Autenticar el inicio de sesión del usuario
     * @param username Username o email
     * @param password Contraseña
     * @return Respuesta de autenticación con token JWT
     */
    AuthResponseDTO login(String username, String password);

    /**
     * Actualizar el token JWT
     * @param refreshToken Token de actualización
     * @return Nueva respuesta de autenticación
     */
    AuthResponseDTO refreshToken(String refreshToken);

    /**
     * Cerrar sesión de usuario (invalidar token)
     * @param token Token JWT para invalidar
     */
    void logout(String token);

    /**
     * Validar token JWT
     * @param token JWT token
     * @return verdadero si es válido, falso en caso contrario
     */
    boolean validateToken(String token);

    /**
     * Obtener el nombre de usuario a partir del token JWT
     * @param token JWT token
     * @return Username
     */
    String getUsernameFromToken(String token);
}

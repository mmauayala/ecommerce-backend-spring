package com.miempresa.ecommerce.dto.response;

import java.util.Set;

import com.miempresa.ecommerce.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO para respuestas de autenticación
 * 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<User.Role> roles;

}

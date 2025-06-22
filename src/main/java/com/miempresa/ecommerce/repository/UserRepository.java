package com.miempresa.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miempresa.ecommerce.entity.User;

/**
 * Interfaz de repositorio para operaciones de entidades de usuario
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Buscar usuario por nombre de usuario
     * @param username Username
     * @return Optional User
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     * @param email Email address
     * @return Optional User
     */
    Optional<User> findByEmail(String email);

    /**
     * Buscar usuario por nombre de usuario o email
     * @param username Username
     * @param email Email address
     * @return Optional User
     */
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * Comprobar si existe el nombre de usuario
     * @param username Username
     * @return true si existe, falso en caso contrario
     */
    boolean existsByUsername(String username);

    /**
     * Comprobar si existe el email
     * @param email Email address
     * @return true si existe, falso en caso contrario
     */
    boolean existsByEmail(String email);
}

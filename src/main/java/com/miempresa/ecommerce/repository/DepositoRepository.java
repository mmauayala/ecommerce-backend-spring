package com.miempresa.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miempresa.ecommerce.entity.Deposito;

/**
 * Repositorio para operaciones de entidades de depósito
 * 
 */
@Repository
public interface DepositoRepository extends JpaRepository<Deposito, Long> {

    /**
     * Encontrar todos los depósitos activos
     * @return Lista de depósitos activos
     */
    List<Deposito> findByActiveTrueOrderByName();

    /**
     * Encontrar depósito principal
     * @return Optional del depósito principal
     */
    Optional<Deposito> findByIsMainTrueAndActiveTrue();

    /**
     * Encontrar depósitos por ciudad
     * @param city Ciudad
     * @return Lista de depósitos en la ciudad
     */
    List<Deposito> findByCityAndActiveTrueOrderByName(String city);

    /**
     * Verificar si existe un depósito con el nombre
     * @param name Nombre del depósito
     * @return true si existe
     */
    boolean existsByNameAndActiveTrue(String name);
}

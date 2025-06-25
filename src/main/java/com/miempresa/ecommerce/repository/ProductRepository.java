package com.miempresa.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miempresa.ecommerce.entity.Product;

/**
 * Interfaz de repositorio para operaciones de entidades de producto
 * 
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Buscar producto por SKU
     * @param sku Product SKU
     * @return Optional Product
     */
    Optional<Product> findBySku(String sku);

    /**
     * Encuentra todos los productos activos
     * @param pageable Informacion de Paginacion
     * @return Page de los productos activos
     */
    Page<Product> findByActiveTrue(Pageable pageable);

    /**
     * Encuentra productos por categoría
     * @param category Categoria de producto 
     * @param pageable Informacion de Paginacion
     * @return Page de los productos en category
     */
    Page<Product> findByCategoryAndActiveTrue(String category, Pageable pageable);

    /**
     * Encuentra productos con bajo stock
     * @param threshold Stock threshold
     * @return List de productos con bajo stock
     */
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.stockQuantity <= :threshold")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);

    /**
     * Comprobar si existe el SKU
     * @param sku Product SKU
     * @return true si existe, falso en caso contrario
     */
    boolean existsBySku(String sku);
}

package com.miempresa.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miempresa.ecommerce.entity.ProductStock;

/**
 * Repositorio para operaciones de stock de productos
 * 
 */
@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    /**
     * Encontrar stock por producto y depósito
     * @param productId ID del producto
     * @param depositoId ID del depósito
     * @return Optional del stock
     */
    @Query("SELECT ps FROM ProductStock ps WHERE ps.product.id = :productId AND ps.deposito.id = :depositoId")
    Optional<ProductStock> findByProductIdAndDepositoId(@Param("productId") Long productId, 
                                                        @Param("depositoId") Long depositoId);

    /**
     * Encontrar todos los stocks de un producto
     * @param productId ID del producto
     * @return Lista de stocks del producto
     */
    @Query("SELECT ps FROM ProductStock ps WHERE ps.product.id = :productId")
    List<ProductStock> findByProductId(@Param("productId") Long productId);

    /**
     * Encontrar stocks con bajo inventario
     * @return Lista de stocks con cantidad <= stock mínimo
     */
    @Query("SELECT ps FROM ProductStock ps WHERE ps.quantity <= ps.minStock")
    List<ProductStock> findLowStockItems();

    /**
     * Calcular stock total de un producto
     * @param productId ID del producto
     * @return Stock total
     */
    @Query("SELECT COALESCE(SUM(ps.quantity), 0) FROM ProductStock ps WHERE ps.product.id = :productId")
    Integer getTotalStockByProductId(@Param("productId") Long productId);

    /**
     * Calcular stock disponible total de un producto
     * @param productId ID del producto
     * @return Stock disponible total
     */
    @Query("SELECT COALESCE(SUM(ps.quantity - ps.reservedQuantity), 0) FROM ProductStock ps WHERE ps.product.id = :productId")
    Integer getAvailableStockByProductId(@Param("productId") Long productId);
}

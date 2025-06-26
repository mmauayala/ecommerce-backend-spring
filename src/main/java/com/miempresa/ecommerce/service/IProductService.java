package com.miempresa.ecommerce.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miempresa.ecommerce.dto.request.ProductRequestDTO;
import com.miempresa.ecommerce.dto.response.ProductResponseDTO;

/**
 * Interfaz de servicio para la lógica de negocio del producto
 * 
 */
public interface IProductService {

    /**
     * Crear un nuevo producto
     * @param productRequest Solicitud de creación de producto
     * @return Respuesta de creación de producto
     */
    ProductResponseDTO createProduct(ProductRequestDTO productRequest);

    /**
     * Obtener producto por ID
     * @param id ID del producto
     * @return Respuesta del producto
     */
    ProductResponseDTO getProductById(Long id);

    /**
     * Obtener producto por SKU
     * @param sku SKU del producto
     * @return Respuesta del producto
     */
    ProductResponseDTO getProductBySku(String sku);

    /**
     * Obtener todos los productos activos con paginación
     * @param pageable Información de paginación
     * @return Page de respuestas del producto
     */
    Page<ProductResponseDTO> getAllActiveProducts(Pageable pageable);

    /**
     * Obtener productos por categoría con paginación
     * @param category Categoría del producto
     * @param pageable Información de paginación
     * @return Page de respuestas del producto
     */
    Page<ProductResponseDTO> getProductsByCategory(String category, Pageable pageable);

    /**
     * Buscar productos por keyword 
     * @param keyword Buscar keyword 
     * @param pageable Información de paginación
     * @return Page de respuestas de productos coincidentes
     */
    Page<ProductResponseDTO> searchProducts(String keyword, Pageable pageable);

    /**
     * Actualizar producto
     * @param id ID del producto
     * @param productRequest Solicitud de actualización del producto
     * @return Respuesta de actualización del producto
     */
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequest);

    /**
     * Eliminar producto (eliminación temporal)
     * @param id ID del producto
     */
    void deleteProduct(Long id);

    /**
     * Obtener productos con poco stock
     * @param umbral Umbral de stock
     * @return Lista de productos con poco stock
     */
    List<ProductResponseDTO> getLowStockProducts(Integer threshold);

    /**
     * Actualizar stock de producto
     * @param id ID del producto
     * @param amount Nueva cantidad de stock
     * @return Respuesta actualizada del producto
     */
    ProductResponseDTO updateStock(Long id, Integer quantity);
}

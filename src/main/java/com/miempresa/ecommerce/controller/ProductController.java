package com.miempresa.ecommerce.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miempresa.ecommerce.dto.request.ProductCreateRequestDTO;
import com.miempresa.ecommerce.dto.request.ProductUpdateRequestDTO;
import com.miempresa.ecommerce.dto.response.ProductResponseDTO;
import com.miempresa.ecommerce.service.IProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para operaciones de producto
 * 
 */
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Products", description = "Endpoints para gestión de productos")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    /**
     * Crear un nuevo producto
     * @param productRequest Solicitud de creación de producto
     * @return Respuesta del producto creado
     */
    @Operation(
    summary = "Crear nuevo producto",
    description = "Crea un nuevo producto en el sistema (requiere rol ADMIN)",
    security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ProductResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado"),
        @ApiResponse(responseCode = "403", description = "Acceso denegado - requiere rol ADMIN")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateRequestDTO productRequest) {
        logger.info("Solicitud REST para crear un producto: {}", productRequest.getName());
        
        // TODO: Implementar el punto final de creación del producto 
        // ProductResponseDTO response = productService.createProduct(productRequest); 
        // return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
        throw new UnsupportedOperationException("Endpoint not implemented yet");
    }

    /**
     * Obtener producto por ID
     * @param id Product ID
     * @return Respuesta de Product 
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        logger.info("REST request to get product by ID: {}", id);
        
        // TODO: Implementar el punto final de obtención de producto por ID
        
        throw new UnsupportedOperationException("Endpoint not implemented yet");
    }

    /**
     * Obtener producto por SKU
     * @param sku Product SKU
     * @return Respuesta de Product 
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDTO> getProductBySku(@PathVariable String sku) {
        logger.info("REST request to get product by SKU: {}", sku);
        
        // TODO: Implementar la obtención de producto por punto final de SKU
        
        throw new UnsupportedOperationException("Endpoint not implemented yet");
    }

    /**
     * Obtenga todos los productos activos con paginación
     * @param page Número de página (predeterminado: 0) 
     * @param size Tamaño de página (predeterminado: 20) 
     * @param sortBy Campo de ordenación (predeterminado: id) 
     * @param sortDir Dirección de ordenación (predeterminado: asc) 
     * @return Page de respuestas del producto
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        logger.info("Solicitud REST para obtener todos los productos activos - page: {}, size: {}", page, size);
        
        // TODO: Implementar el punto final para obtener todos los productos activos
        
        throw new UnsupportedOperationException("Punto final aún no implementado");
    }

    /**
     * Obtener productos por categoría 
     * @param category Categoría del producto 
     * @param page Número de página (predeterminado: 0)
     * @param size Tamaño de página (predeterminado: 20) 
     * @return Page de respuestas del producto */
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<ProductResponseDTO>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        logger.info("REST request to get products by category: {}", category);
        
        // TODO: Implement get products by category endpoint
        
        throw new UnsupportedOperationException("Endpoint aún no implementado");
    }

    /**
     * Buscar productos por palabra clave 
     * @param palabra clave Buscar palabra clave 
     * @param página Número de página (predeterminado: 0) 
     * @param tamaño Tamaño de página (predeterminado: 20) 
     * @return Página de respuestas de productos coincidentes 
    */

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        logger.info("Solicitud REST para buscar productos con palabra clave: {}", keyword);
        
        // TODO: Implementar el punto final de productos de búsqueda
        
        throw new UnsupportedOperationException("Endpoint aún no implementado");
    }

    /**
     * Actualizar producto 
     * @param id ID del producto 
     * @param productRequest Solicitud de actualización del producto * @return Respuesta de actualización del producto 
     */

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequestDTO productRequest) {
        
        logger.info("Solicitud REST para actualizar el producto con ID: {}", id);
        
        // TODO: Implementar el punto final de actualización del producto
        
        throw new UnsupportedOperationException("Endpoint aún no implementado");
    }

    /**
     * Borrar product (soft delete)
     * @param id Product ID
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        logger.info("Solicitud REST para eliminar producto con ID: {}", id);
        
        // TODO: Implementar la eliminación del punto final del producto
        
        throw new UnsupportedOperationException("Endpoint aún no implementado");
    }

    /**
     * Obtener productos con poco stock 
     * @param umbral Umbral de stock (predeterminado: 10) 
     * @return Lista de productos con poco stock 
     */

    @GetMapping("/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponseDTO>> getLowStockProducts(
            @RequestParam(defaultValue = "10") Integer threshold) {
        
        logger.info("Solicitud REST para obtener productos con bajo stock con threshold: {}", threshold);
        
        // TODO: Implementar el punto final para obtener productos con bajo stock
        
        throw new UnsupportedOperationException("Endpoint aún no implementado");
    }

    /**
     * Actualizar stock de producto 
     * @param id ID del producto 
     * @param amount Nueva cantidad de stock 
     * @return Respuesta actualizada del producto 
     */

    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> updateStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        
        logger.info("Solicitud REST para actualizar el stock del ID del producto: {} a la cantidad: {}", id, quantity);
        
        // TODO: Implementar el punto final de actualización de stock

        
        throw new UnsupportedOperationException("Endpoint aún no implementado");
    }
}

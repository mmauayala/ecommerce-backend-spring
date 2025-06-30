package com.miempresa.ecommerce.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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
        
        ProductResponseDTO response = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        
        //throw new UnsupportedOperationException("Punto final aún no implementado");
    }
    
    /**
     * Obtener producto por ID
     * @param id Product ID
     * @return Respuesta de Product 
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        logger.info("Solicitud REST para obtener el producto por ID: {}", id);
        
        ProductResponseDTO response = productService.getProductById(id);
        return ResponseEntity.ok(response);
        
        //throw new UnsupportedOperationException("Punto final aún no implementado");
    }

    /**
     * Obtener producto por SKU
     * @param sku Product SKU
     * @return Respuesta de Product 
     */
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDTO> getProductBySku(@PathVariable String sku) {
        logger.info("Solicitud REST para obtener el producto por SKU: {}", sku);
        
        ProductResponseDTO response = productService.getProductBySku(sku);
        return ResponseEntity.ok(response);
        
        //throw new UnsupportedOperationException("Punto final aún no implementado");
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
        
        logger.info("Solicitud REST para obtener todos los productos activos - página: {}, tamaño: {}", page, size);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProductResponseDTO> products = productService.getAllActiveProducts(pageable);
        return ResponseEntity.ok(products);
        
        // throw new UnsupportedOperationException("Punto final aún no implementado");
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
        
        logger.info("Solicitud REST para obtener productos por categoría: {}", category);

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> products = productService.getProductsByCategory(category, pageable);
        return ResponseEntity.ok(products);
        
        //throw new UnsupportedOperationException("Endpoint aún no implementado");
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

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponseDTO> products = productService.searchProducts(keyword, pageable);
        return ResponseEntity.ok(products);
        
        //throw new UnsupportedOperationException("Endpoint aún no implementado");
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

        ProductResponseDTO response = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(response);
        
        //throw new UnsupportedOperationException("Endpoint aún no implementado");
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

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
        
        //throw new UnsupportedOperationException("Endpoint aún no implementado");
    }

    /**
     * Obtener productos con poco stock 
     * @param threshold Umbral de stock (predeterminado: 10) 
     * @return Lista de productos con poco stock 
     */

    @GetMapping("/low-stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductResponseDTO>> getLowStockProducts(
            @RequestParam(defaultValue = "10") Integer threshold) {
        
        logger.info("Solicitud REST para obtener productos con bajo stock con threshold: {}", threshold);

        List<ProductResponseDTO> products = productService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
        
        //throw new UnsupportedOperationException("Endpoint aún no implementado");
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

        ProductResponseDTO response = productService.updateStock(id, quantity);
        return ResponseEntity.ok(response);
        
        //throw new UnsupportedOperationException("Endpoint aún no implementado");
    }
}

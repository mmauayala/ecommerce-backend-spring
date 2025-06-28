package com.miempresa.ecommerce.dto.request;

import com.miempresa.ecommerce.validation.ValidPrice;
import com.miempresa.ecommerce.validation.ValidSku;
import com.miempresa.ecommerce.validation.ValidStock;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para creación de productos con validaciones completas
 * 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 255, message = "El nombre del producto no debe exceder los 255 caracteres")
    private String name;

    @Size(max = 1000, message = "La descripción no debe exceder los 1000 caracteres")
    private String description;

    @NotNull(message = "Se requiere precio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @ValidPrice
    private Double price;

    @NotNull(message = "Se requiere cantidad de stock")
    @ValidStock
    private Integer stockQuantity;

    @ValidSku
    private String sku;

    private Long categoryId;

    private String imageUrl;

    private Boolean active = true;

    @ValidStock
    private Integer minStock = 0;
}

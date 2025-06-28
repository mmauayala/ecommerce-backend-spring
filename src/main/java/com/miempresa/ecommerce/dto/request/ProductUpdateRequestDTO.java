package com.miempresa.ecommerce.dto.request;

import com.miempresa.ecommerce.validation.ValidPrice;
import com.miempresa.ecommerce.validation.ValidStock;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para actualización de productos (campos opcionales)
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequestDTO {

    @Size(max = 255, message = "El nombre del producto no debe exceder los 255 caracteres")
    private String name;

    @Size(max = 1000, message = "La descripción no debe exceder los 1000 caracteres")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    @ValidPrice
    private Double price;

    @ValidStock
    private Integer stockQuantity;

    private Long categoryId;

    private String imageUrl;

    private Boolean active;
}

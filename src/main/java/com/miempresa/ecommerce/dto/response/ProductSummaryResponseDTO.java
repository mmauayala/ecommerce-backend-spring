package com.miempresa.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de resumen de producto para listados
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer stockQuantity;
    private String sku;
    private String imageUrl;
    private Boolean active;
    private String categoryName;

}

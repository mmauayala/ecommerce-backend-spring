package com.miempresa.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de resumen de categoría para listados
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySummaryResponseDTO {

    private Long id;
    private String name;
    private String slug;
    private String imageUrl;
    private Boolean active;
    private Integer sortOrder;
    private Boolean hasChildren;

}

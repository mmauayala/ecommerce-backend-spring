package com.miempresa.ecommerce.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta de categoría con productos asociados
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String slug;
    private String imageUrl;
    private Boolean active;
    private Integer sortOrder;
    private Long parentId;
    private String parentName;
    private List<CategorySummaryResponseDTO> children;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
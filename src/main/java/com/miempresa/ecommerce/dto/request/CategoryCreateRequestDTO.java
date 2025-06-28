package com.miempresa.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para creación de categorías
 * 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreateRequestDTO {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(max = 100, message = "El nombre de la categoría no debe exceder los 100 caracteres")
    private String name;

    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    private String description;

    private String slug;

    private String imageUrl;

    private Boolean active = true;

    private Integer sortOrder = 0;

    private Long parentId;
}

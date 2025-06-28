package com.miempresa.ecommerce.util;

import java.util.List;
import java.util.stream.Collectors;

import com.miempresa.ecommerce.dto.request.CategoryCreateRequestDTO;
import com.miempresa.ecommerce.dto.request.ProductCreateRequestDTO;
import com.miempresa.ecommerce.dto.response.CategoryResponseDTO;
import com.miempresa.ecommerce.dto.response.CategorySummaryResponseDTO;
import com.miempresa.ecommerce.dto.response.ProductResponseDTO;
import com.miempresa.ecommerce.dto.response.ProductSummaryResponseDTO;
import com.miempresa.ecommerce.entity.Category;
import com.miempresa.ecommerce.entity.Product;

/**
 * Utilidades para mapeo entre DTOs y Entidades
 * 
 */
public final class MapperUtil {

    private MapperUtil() {
        throw new UnsupportedOperationException("Esta es una clase de utilidad y no se puede instanciar");
    }

    // Mappers de Producto 
    public static ProductResponseDTO toProductResponseDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setSku(product.getSku());
        dto.setCategory(product.getCategory() != null ? product.getCategory().getName() : null);
        dto.setImageUrl(product.getImageUrl());
        dto.setActive(product.getActive());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());

        return dto;
    }

    public static ProductSummaryResponseDTO toProductSummaryDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductSummaryResponseDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getSku(),
            product.getImageUrl(),
            product.getActive(),
            product.getCategory() != null ? product.getCategory().getName() : null
        );
    }

    public static Product toProductEntity(ProductCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setSku(dto.getSku());
        product.setImageUrl(dto.getImageUrl());
        product.setActive(dto.getActive());

        return product;
    }

    // Mappers de Categoria 
    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        if (category == null) {
            return null;
        }

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setSlug(category.getSlug());
        dto.setImageUrl(category.getImageUrl());
        dto.setActive(category.getActive());
        dto.setSortOrder(category.getSortOrder());
        dto.setParentId(category.getParent() != null ? category.getParent().getId() : null);
        dto.setParentName(category.getParent() != null ? category.getParent().getName() : null);
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());

        // Mapear hijos
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            dto.setChildren(category.getChildren().stream()
                    .map(MapperUtil::toCategorySummaryDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static CategorySummaryResponseDTO toCategorySummaryDTO(Category category) {
        if (category == null) {
            return null;
        }

        return new CategorySummaryResponseDTO(
            category.getId(),
            category.getName(),
            category.getSlug(),
            category.getImageUrl(),
            category.getActive(),
            category.getSortOrder(),
            category.hasChildren()
        );
    }

    public static Category toCategoryEntity(CategoryCreateRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setSlug(dto.getSlug());
        category.setImageUrl(dto.getImageUrl());
        category.setActive(dto.getActive());
        category.setSortOrder(dto.getSortOrder());

        return category;
    }

    // List Mappers
    public static List<ProductSummaryResponseDTO> toProductSummaryDTOList(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream()
                .map(MapperUtil::toProductSummaryDTO)
                .collect(Collectors.toList());
    }

    public static List<CategorySummaryResponseDTO> toCategorySummaryDTOList(List<Category> categories) {
        if (categories == null) {
            return null;
        }

        return categories.stream()
                .map(MapperUtil::toCategorySummaryDTO)
                .collect(Collectors.toList());
    }
}

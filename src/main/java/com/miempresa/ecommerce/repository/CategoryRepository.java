package com.miempresa.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.miempresa.ecommerce.entity.Category;

/**
 * Repositorio para operaciones de categorías
 * 
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Encontrar categoría por slug
     * @param slug Slug de la categoría
     * @return Optional de la categoría
     */
    Optional<Category> findBySlugAndActiveTrue(String slug);

    /**
     * Encontrar categorías raíz (sin padre)
     * @return Lista de categorías raíz activas
     */
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL AND c.active = true ORDER BY c.sortOrder, c.name")
    List<Category> findRootCategories();

    /**
     * Encontrar subcategorías de una categoría padre
     * @param parentId ID de la categoría padre
     * @return Lista de subcategorías activas
     */
    @Query("SELECT c FROM Category c WHERE c.parent.id = :parentId AND c.active = true ORDER BY c.sortOrder, c.name")
    List<Category> findByParentId(@Param("parentId") Long parentId);

    /**
     * Verificar si existe una categoría con el slug
     * @param slug Slug
     * @return true si existe
     */
    boolean existsBySlug(String slug);

    /**
     * Encontrar todas las categorías activas
     * @return Lista de categorías activas ordenadas
     */
    List<Category> findByActiveTrueOrderBySortOrderAscNameAsc();
}

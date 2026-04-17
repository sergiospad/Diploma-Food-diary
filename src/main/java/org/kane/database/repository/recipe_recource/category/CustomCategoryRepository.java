package org.kane.database.repository.recipe_recource.category;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.category.CategoryNameDTO;

import java.util.List;
import java.util.Optional;

public interface CustomCategoryRepository {
    List<CategoryNameDTO> findAllCategories();
    Optional<CategoryNameDTO> findCategoryById(Long id);
}

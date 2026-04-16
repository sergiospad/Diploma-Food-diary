package org.kane.database.repository.recipe_recource.category;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.category.CategoryNameDTO;

import java.util.List;

public interface CustomCategoryRepository {
    List<CategoryNameDTO> findAllCategories();
}

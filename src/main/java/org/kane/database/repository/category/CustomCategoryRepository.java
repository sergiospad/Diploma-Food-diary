package org.kane.database.repository.category;

import org.kane.domain.DTO.entityDTO.category.CategoryNameDTO;

import java.util.List;

public interface CustomCategoryRepository {
    List<CategoryNameDTO> findAllCategories();
}

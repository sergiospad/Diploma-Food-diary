package org.kane.database.repository.category;

import org.kane.domain.DTO.entityDTO.category.CategoryNameDTO;

import java.util.List;
import java.util.Optional;

public interface CustomCategoryRepository {
    List<CategoryNameDTO> findAllCategories();
}

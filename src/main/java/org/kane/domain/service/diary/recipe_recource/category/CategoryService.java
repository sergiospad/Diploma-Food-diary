package org.kane.domain.service.diary.recipe_recource.category;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.category.CategoryCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.category.CategoryNameDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.category.CategoryShowDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryNameDTO> getAll();

    CategoryShowDTO createCategory(CategoryCreateDTO categoryCreateDTO);

    List<CategoryShowDTO> getAllShowDTO();

    void editCategory(CategoryNameDTO categoryNameDTO);
}

package org.kane.domain.service.category;

import org.kane.domain.DTO.entityDTO.category.CategoryCreateDTO;
import org.kane.domain.DTO.entityDTO.category.CategoryNameDTO;
import org.kane.domain.DTO.entityDTO.category.CategoryShowDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryNameDTO> getAll();

    CategoryShowDTO createCategory(CategoryCreateDTO categoryCreateDTO);

    List<CategoryShowDTO> getAllShowDTO();

    void editCategory(CategoryNameDTO categoryNameDTO);
}

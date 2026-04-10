package org.kane.domain.service.category;

import org.kane.domain.DTO.entityDTO.category.CategoryNameDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryNameDTO> getAll();
}

package org.kane.domain.service.category;

import org.kane.database.repository.category.CategoryRepository;
import org.kane.domain.DTO.entityDTO.category.CategoryNameDTO;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Override
    public List<CategoryNameDTO> getAll() {
        return categoryRepository.findAllCategories();
    }
}

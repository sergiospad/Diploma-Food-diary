package org.kane.domain.mappers;

import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryNameDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryShowDTO;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperShow implements Mapper<CategoryNameDTO, CategoryShowDTO> {
    @Override
    public CategoryShowDTO map(CategoryNameDTO from) {
        return CategoryShowDTO.builder()
                .id(from.getId())
                .name(from.getName())
                .build();
    }
}

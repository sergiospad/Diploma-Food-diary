package org.kane.database.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRecipeRepository {
    Page<RecipePreviewDTO> findAllPreviewDTO(BooleanBuilder predicate, Pageable pageable);
}

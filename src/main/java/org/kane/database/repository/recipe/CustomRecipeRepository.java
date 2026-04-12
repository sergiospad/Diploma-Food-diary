package org.kane.database.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreShowProjection;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeSummarySearchDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeTitleSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomRecipeRepository {
    Page<RecipePreviewDTO> findAllPreviewDTO(BooleanBuilder predicate, Pageable pageable);
    List<RecipeSummarySearchDTO>  findSummaryDTOByItem(String searchItem);
    List<RecipeTitleSearchDTO> findTitleDTOByItem(String searchItem);

    RecipePreShowProjection getRecipePreShowProjByID(Long recipeID);
}

package org.kane.database.repository.recipe_recource.cooking_stage;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageShowDTO;

import java.util.List;

public interface CustomCookingStageRepository {
    List<CookingStageShowDTO> findAllShowDTOByRecipeID(Long recipeID);
}

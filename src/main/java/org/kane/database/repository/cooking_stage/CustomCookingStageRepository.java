package org.kane.database.repository.cooking_stage;

import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageShowDTO;

import java.util.List;

public interface CustomCookingStageRepository {
    List<CookingStageShowDTO> findAllShowDTOByRecipeID(Long recipeID);
}

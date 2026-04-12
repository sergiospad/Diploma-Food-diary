package org.kane.domain.service.cooking_stage;

import org.kane.database.entity.recipe_recource.CookingStage;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageShowDTO;

import java.util.List;

public interface CookingStageService {
    CookingStage createCookingStage(CookingStageCreateDTO cookingStageCreateDTO);
    void editCookingStage(CookingStageEditDescDTO cookingStageEditDescDTO);

    List<CookingStageShowDTO> getAllShowDTOByRecipeID(Long recipeID);
}

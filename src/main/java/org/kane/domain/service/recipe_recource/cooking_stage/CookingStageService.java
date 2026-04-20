package org.kane.domain.service.recipe_recource.cooking_stage;

import org.kane.database.entity.Recipe;
import org.kane.database.entity.recipe_recource.CookingStage;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageShowDTO;

import java.util.List;

public interface CookingStageService {
    CookingStage createCookingStage(CookingStageCreateDTO cookingStageCreateDTO, Recipe recipe);
    void editCookingStage(CookingStageEditDescDTO cookingStageEditDescDTO);

    List<CookingStageShowDTO> getAllShowDTOByRecipeID(Long recipeID);

    void removeCookingStage(Long stageID);
}

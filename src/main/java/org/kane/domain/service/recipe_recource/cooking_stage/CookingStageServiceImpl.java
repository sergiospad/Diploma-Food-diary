package org.kane.domain.service.recipe_recource.cooking_stage;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.recipe_recource.CookingStage;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.recipe_recource.cooking_stage.CookingStageRepository;
import org.kane.database.repository.recipe_recource.image_model.ImageModelRepository;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageShowDTO;
import org.kane.exceptions.not_found.CookingStageNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CookingStageServiceImpl implements CookingStageService {
    private final ImageModelRepository imageModelRepository;
    private final CookingStageRepository cookingStageRepository;
    private final RecipeRepository recipeRepository;

    @Transactional
    @Override
    public CookingStage createCookingStage(CookingStageCreateDTO cookingStageCreateDTO, Recipe recipe) {
        return cookingStageRepository.save(CookingStage.builder()
                .stageNumber(cookingStageCreateDTO.getStageNumber())
                .description(cookingStageCreateDTO.getDescription())
                .image(imageModelRepository
                        .findById(cookingStageCreateDTO.getImageID())
                        .orElse(null))
                .recipe(recipe)
                .build());
    }
    @Transactional
    @Override
    public void editCookingStage(CookingStageEditDescDTO cookingStageEditDescDTO) {
        var cs = cookingStageRepository.findById(cookingStageEditDescDTO.getId())
                .orElseThrow(() -> new CookingStageNotFoundException("cooking stage not found"));
        cs.setDescription(cookingStageEditDescDTO.getDescription());
        cookingStageRepository.save(cs);
    }

    @Override
    public List<CookingStageShowDTO> getAllShowDTOByRecipeID(Long recipeID){
        return cookingStageRepository.findAllShowDTOByRecipeID(recipeID);
    }
    @Transactional
    @Override
    public void removeCookingStage(Long stageID){
        var cookingStage = cookingStageRepository.findById(stageID)
                .orElseThrow(() -> new CookingStageNotFoundException("cooking stage not found"));
        var recipe = cookingStage.getRecipe();
        var stageNum = cookingStage.getStageNumber();
        Short stageInt;
        for (var stage: recipe.getCookingStages()){
            stageInt = stage.getStageNumber();
            if (stageInt>stageNum)
                stage.setStageNumber((short)(stageInt - 1));
        }
        recipeRepository.save(recipe);
        cookingStageRepository.delete(cookingStage);

    }
}

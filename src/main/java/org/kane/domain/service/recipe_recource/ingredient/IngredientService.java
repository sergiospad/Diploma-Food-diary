package org.kane.domain.service.recipe_recource.ingredient;

import org.kane.database.entity.Recipe;
import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient.IngredientChangeDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient.IngredientCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient.IngredientEditDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient.IngredientShowDTO;

import java.util.List;

public interface IngredientService {
    Ingredient createIngredient(IngredientCreateDTO ingredientCreateDTO, Recipe recipe);
    Ingredient updateIngredient(IngredientEditDTO ingredient);
    void removeIngredient(Long id);
    List<IngredientShowDTO> getShowIngredients(Long recipeID);
    IngredientShowDTO toggleMeasureUnit(IngredientChangeDTO ingredientChangeDTO);
}

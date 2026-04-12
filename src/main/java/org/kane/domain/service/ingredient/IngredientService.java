package org.kane.domain.service.ingredient;

import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientChangeDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientCreateDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientEditDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientShowDTO;

import java.util.List;

public interface IngredientService {
    Ingredient createIngredient(IngredientCreateDTO ingredientCreateDTO);
    Ingredient updateIngredient(IngredientEditDTO ingredient);
    void removeIngredient(Long id);
    List<IngredientShowDTO> getShowIngredients(Long recipeID);
    IngredientShowDTO toggleMeasureUnit(IngredientChangeDTO ingredientChangeDTO);
}

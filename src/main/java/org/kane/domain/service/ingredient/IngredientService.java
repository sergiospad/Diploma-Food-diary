package org.kane.domain.service.ingredient;

import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientCreateDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientEditDTO;

public interface IngredientService {
    Ingredient createIngredient(IngredientCreateDTO ingredientCreateDTO);
    Ingredient updateIngredient(IngredientEditDTO ingredient);
    void removeIngredient(Long id);
}

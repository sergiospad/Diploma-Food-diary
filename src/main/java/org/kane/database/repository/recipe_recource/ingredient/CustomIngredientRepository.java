package org.kane.database.repository.recipe_recource.ingredient;

import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientEnergyDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientPreShowProjection;

import java.util.List;

public interface CustomIngredientRepository {
    List<IngredientPreShowProjection> findPreShowDTO(Long recipeID);
    IngredientPreShowProjection findPreShowDTOById(Long ingredientID);

    List<IngredientEnergyDTO> findIngredientEnergyDTOByRecipeID(Long recipeID);
}

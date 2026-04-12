package org.kane.database.repository.ingredient;

import org.kane.domain.DTO.entityDTO.ingredient.IngredientEnergyDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientPreShowProjection;

import java.util.List;

public interface CustomIngredientRepository {
    List<IngredientPreShowProjection> findPreShowDTO(Long recipeID);
    IngredientPreShowProjection findPreShowDTOById(Long ingredientID);

    List<IngredientEnergyDTO> findIngredientEnergyDTOByRecipeID(Long recipeID);
}

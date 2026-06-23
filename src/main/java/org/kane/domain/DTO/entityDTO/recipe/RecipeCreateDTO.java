package org.kane.domain.DTO.entityDTO.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientCreateDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCreateDTO {
    private String name;
    private String summary;
    private Long illustrationID;
    private Boolean isPrivate;
    private List<Long> tags;
    private Short cookingTime;
    private List<IngredientCreateDTO> ingredients;
    private List<CookingStageCreateDTO> stages;
}

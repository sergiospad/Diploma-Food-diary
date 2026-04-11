package org.kane.domain.DTO.entityDTO.recipe;

import lombok.Data;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientEditDTO;

import java.util.List;

@Data
public class RecipeEditDTO {
    private Long id;
    private String name;
    private String summary;
    private Short cookingTime;
    private List<Long> addTags;
    private List<Long> removeTags;
    private Boolean isPrivate;
    private List<CookingStageEditDescDTO> editedStages;
    private List<IngredientEditDTO> editedIngredients;
}

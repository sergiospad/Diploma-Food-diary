package org.kane.domain.DTO.entityDTO.recipe;

import lombok.Data;
import org.kane.domain.DTO.entityDTO.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientCreateDTO;

import java.util.List;

@Data
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

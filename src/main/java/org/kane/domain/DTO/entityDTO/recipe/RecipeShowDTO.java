package org.kane.domain.DTO.entityDTO.recipe;

import lombok.Builder;
import lombok.Data;
import org.kane.domain.DTO.entityDTO.EnergyValueShowDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.TagDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageShowDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientShowDTO;

import java.util.List;

@Data
@Builder
public class RecipeShowDTO {
    private Long id;
    private String authorName;
    private Long avatarID;
    private Short cookingTime;
    private String name;
    private String summary;
    private Long illustrationID;
    private List<TagDTO> tags;
    private List<IngredientShowDTO> ingredients;
    private EnergyValueShowDTO energy;
    private List<CookingStageShowDTO> cookingStages;
}

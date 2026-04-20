package org.kane.domain.DTO.entityDTO.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.domain.DTO.entityDTO.nutritional_info.EnergyValueShowDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.TagDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageShowDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient.IngredientShowDTO;

import java.util.ArrayList;
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

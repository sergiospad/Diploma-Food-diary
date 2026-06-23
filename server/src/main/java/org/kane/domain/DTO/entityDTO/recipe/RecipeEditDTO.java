package org.kane.domain.DTO.entityDTO.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientEditDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeEditDTO {
    private Long id;
    private String name;
    private String summary;
    private Short cookingTime;

    @Builder.Default
    private List<Long> addTags = new ArrayList<>();

    @Builder.Default
    private List<Long> removeTags= new ArrayList<>();

    private Boolean isPrivate;
    private List<CookingStageEditDescDTO> editedStages;
    private List<IngredientEditDTO> editedIngredients;
}

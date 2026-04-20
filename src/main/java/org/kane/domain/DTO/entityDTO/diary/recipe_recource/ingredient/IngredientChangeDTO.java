package org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientChangeDTO {
    private Long ingredientID;
    private Long measureID;
}

package org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient;

import lombok.Data;

@Data
public class IngredientEditDTO {
    private Long id;
    private Long productID;
    private Double amount;
    private Long measureUnitID;
}

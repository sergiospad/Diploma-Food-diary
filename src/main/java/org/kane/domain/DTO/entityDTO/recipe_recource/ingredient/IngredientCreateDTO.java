package org.kane.domain.DTO.entityDTO.recipe_recource.ingredient;

import lombok.Data;

@Data
public class IngredientCreateDTO {
    private Long productID;
    private Double amount;
    private Long measureUnitID;
}


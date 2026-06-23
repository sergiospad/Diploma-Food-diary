package org.kane.domain.DTO.entityDTO.recipe_recource.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientCreateDTO {
    private Long productID;
    private Double amount;
    private Long measureUnitID;
}


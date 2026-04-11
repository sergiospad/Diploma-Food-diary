package org.kane.domain.DTO.entityDTO.ingredient;

import lombok.Data;

@Data
public class IngredientCreateDTO {
    private Long productID;
    private Double amount;
    private Long measureUnitID;
}


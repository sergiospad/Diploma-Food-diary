package org.kane.domain.DTO.entityDTO.ingredient;

import lombok.Data;
import org.kane.database.entity.physical_quantity.ProductWeight;

@Data
public class IngredientPreShowProjection {
    private Long id;
    private Long productID;
    private ProductWeight amount;
    private Long measureUnitID;
}

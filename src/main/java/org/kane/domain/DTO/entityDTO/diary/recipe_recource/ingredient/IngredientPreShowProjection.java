package org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.ProductWeight;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientPreShowProjection {
    private Long id;
    private Long productID;
    private ProductWeight amount;
    private Long measureUnitID;
}

package org.kane.domain.DTO.entityDTO.recipe_recource.ingredient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientEnergyDTO {
    private Long id;
    private ProductWeight productWeight;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbs carbs;

    public void reevaluate() {
        var coeff = productWeight.getWeightCoefficient();
        calories.multiply(coeff);
        protein.multiply(coeff);
        fat.multiply(coeff);
        carbs.multiply(coeff);
    }
}

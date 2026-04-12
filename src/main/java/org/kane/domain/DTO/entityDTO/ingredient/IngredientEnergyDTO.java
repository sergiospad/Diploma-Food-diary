package org.kane.domain.DTO.entityDTO.ingredient;

import lombok.Data;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;

@Data
public class IngredientEnergyDTO {
    private Long id;
    private ProductWeight productWeight;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbs carbs;
}

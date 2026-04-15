package org.kane.domain.DTO.entityDTO.meal_item;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.domain.DTO.entityDTO.meal.NutritionalInfoDTO;

@EqualsAndHashCode(callSuper = true)
@Data
public class MealItemShowDTO extends NutritionalInfoDTO {
    Long id;
    String name;
    ProductWeight productWeight;

    public MealItemShowDTO(Long id,
                           String name,
                           ProductWeight productWeight,
                           Calories calories,
                           Protein protein,
                           Fat fat,
                           Carbs carbs) {
        this.id = id;
        this.name = name;
        this.productWeight = productWeight;
        setCalories(calories);
        setProteins(protein);
        setFat(fat);
        setCarbs(carbs);
    }
}

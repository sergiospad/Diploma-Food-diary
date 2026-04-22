package org.kane.domain.DTO.entityDTO.diary.meal_item;

import lombok.*;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.enum_types.NutritionType;
import org.kane.domain.DTO.entityDTO.diary.meal.NutritionalInfoDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor

public class MealItemShowDTO extends NutritionalInfoDTO {
    Long id;
    String name;
    ProductWeight productWeight;
    NutritionType nutritionType;

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

    /** Для QueryDSL  */
    public MealItemShowDTO(Long id,
                           String name,
                           ProductWeight productWeight,
                           Calories calories,
                           Protein protein,
                           Fat fat,
                           Carbs carbs,
                           String discriminator) {
        this(id, name, productWeight, calories, protein, fat, carbs);
        if (discriminator != null) {
            this.nutritionType = NutritionType.valueOf(discriminator);
        }
    }
}

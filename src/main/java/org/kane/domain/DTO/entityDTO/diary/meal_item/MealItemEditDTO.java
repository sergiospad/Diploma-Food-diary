package org.kane.domain.DTO.entityDTO.diary.meal_item;

import lombok.Data;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.enum_types.NutritionType;

@Data
public class MealItemEditDTO {
    private Long id;
    private Long nutritionID;
    private NutritionType nutritionType;
    private ProductWeight weight;
}

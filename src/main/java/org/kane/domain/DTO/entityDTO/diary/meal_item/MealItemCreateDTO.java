package org.kane.domain.DTO.entityDTO.diary.meal_item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.enum_types.NutritionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealItemCreateDTO {
    private Long nutritionID;
    private NutritionType nutritionType;
    private ProductWeight weight;
}

package org.kane.domain.DTO.entityDTO.nutritional_info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.NutritionalInfo;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.enum_types.NutritionType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionShowProjection {
    private Long ID;
    private NutritionType type;
    private String name;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbs carbs;

}

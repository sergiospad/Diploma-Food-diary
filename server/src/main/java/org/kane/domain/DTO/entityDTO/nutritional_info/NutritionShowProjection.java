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
public class NutritionShowProjection {
    private Long ID;
    private String name;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbs carbs;
    private NutritionType type;

    public NutritionShowProjection(Long ID,
                                   String name,
                                   Calories calories,
                                   Protein protein,
                                   Fat fat,
                                   Carbs carbs,
                                   NutritionalInfo nutritionalInfo) {
        this.ID = ID;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.type = NutritionType.valueOf(nutritionalInfo.getDiscriminator());
    }
}

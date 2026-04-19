package org.kane.domain.DTO.entityDTO.nutritional_info;

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
    private NutritionType type;
    private String name;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbs carbs;

    public NutritionShowProjection(Long ID,
                                   NutritionType type,
                                   String name,
                                   Calories calories,
                                   Protein protein,
                                   Fat fat,
                                   Carbs carbs) {
        this.ID = ID;
        this.type = type;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public NutritionShowProjection(Long ID,
                                   String name,
                                   Calories calories,
                                   Protein protein,
                                   Fat fat,
                                   Carbs carbs,
                                   NutritionalInfo nutrition){
        this.ID = ID;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.type = nutrition instanceof org.kane.database.entity.Product ? NutritionType.PRODUCT : NutritionType.RECIPE;

    }
}

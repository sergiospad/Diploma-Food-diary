package org.kane.domain.DTO.entityDTO.meal_item;

import lombok.*;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.domain.DTO.entityDTO.meal.NutritionalInfoDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class TotalMealShowDTO extends NutritionalInfoDTO {
    public TotalMealShowDTO() {
        super();
    }


}

package org.kane.domain.DTO.entityDTO.diary.meal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.MealType;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;

import java.time.LocalTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor

public class MealShowDTO extends NutritionalInfoDTO {
    Long id;
    LocalTime time;
    MealType mealType;
    List<MealItemShowDTO> showDTOList;

    public MealShowDTO(Long id,
                       MealType mealType,
                       Calories calories,
                       Protein protein,
                       Fat fat,
                       Carbs carbs,
                       LocalTime time,
                       List<MealItemShowDTO> mealItemShowDTO) {
        this.id = id;
        this.mealType = mealType;
        this.showDTOList = mealItemShowDTO;
        this.setCalories(calories);
        this.setProteins(protein);
        this.setFat(fat);
        this.setCarbs(carbs);
    }
}

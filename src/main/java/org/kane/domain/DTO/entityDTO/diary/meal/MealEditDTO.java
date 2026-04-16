package org.kane.domain.DTO.entityDTO.diary.meal;

import lombok.Data;
import org.kane.database.enum_types.MealType;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class MealEditDTO {
    private Long mealID;
    private MealType mealType;
    private LocalTime time;
    private List<MealItemEditDTO> mealItemEditDTOList;
}

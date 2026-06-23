package org.kane.domain.DTO.entityDTO.diary.meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.MealType;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemCreateDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealCreateDTO {
    private LocalDate dailyRecordDate;
    private MealType mealType;
    private LocalTime time;
    private List<MealItemCreateDTO> list;
}

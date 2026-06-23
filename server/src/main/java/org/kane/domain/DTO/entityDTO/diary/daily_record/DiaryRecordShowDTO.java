package org.kane.domain.DTO.entityDTO.diary.daily_record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.domain.DTO.entityDTO.diary.meal.MealShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.TotalMealShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.CalorieConsumptionShowDTO;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiaryRecordShowDTO {
    LocalDate date;
    List<MealShowDTO> meals;
    TotalMealShowDTO total;
    CalorieConsumptionShowDTO calorieConsumption;
}

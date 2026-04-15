package org.kane.domain.DTO.entityDTO.daily_record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.domain.DTO.entityDTO.meal.MealShowDTO;
import org.kane.domain.DTO.entityDTO.meal_item.TotalMealShowDTO;
import org.kane.domain.DTO.entityDTO.sport_activity.CalorieConsumptionShowDTO;

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

package org.kane.database.repository.diary.meal;

import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CustomMealRepository {

    Map<MealProjection, List<MealItemShowDTO>> getShowDTOMap(LocalDate recordDate, Long userID);

    List<MealItemShowDTO> showMealItems(Long mealID);
}

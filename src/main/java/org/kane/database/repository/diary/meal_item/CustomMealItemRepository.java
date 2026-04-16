package org.kane.database.repository.diary.meal_item;

import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;

import java.util.List;

public interface CustomMealItemRepository {
    List<MealItemShowDTO> getMealItemsOfMeal(Long meal);
}

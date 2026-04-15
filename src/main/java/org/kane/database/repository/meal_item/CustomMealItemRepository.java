package org.kane.database.repository.meal_item;

import org.kane.domain.DTO.entityDTO.meal_item.MealItemShowDTO;

import java.util.List;

public interface CustomMealItemRepository {
    List<MealItemShowDTO> getMealItemsOfMeal(Long meal);
}

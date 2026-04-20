package org.kane.domain.service.diary.meal_item;

import org.kane.database.entity.diary.MealItem;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;

public interface MealItemService {
    MealItem unpackMealItem(MealItemCreateDTO mealItemCreateDTO);

    void updateMealItem(MealItemEditDTO mealItemEditDTO);
}

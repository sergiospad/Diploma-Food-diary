package org.kane.domain.service.diary.meal_item;

import org.kane.database.entity.diary.Meal;
import org.kane.database.entity.diary.MealItem;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;
import org.springframework.transaction.annotation.Transactional;

public interface MealItemService {
    void unpackMealItem(MealItemCreateDTO mealItemCreateDTO, Meal meal);

    void updateMealItem(MealItemEditDTO mealItemEditDTO);

    @Transactional
    void deleteMealItem(Long id);
}

package org.kane.domain.service.diary.meal;

import org.kane.domain.DTO.entityDTO.diary.meal.MealCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealEditDTO;
import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal.MealShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.TotalMealShowDTO;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface MealService {
    @Transactional
    MealShowDTO createMeal(Principal principal, MealCreateDTO mealCreateDTO);

    MealShowDTO getMealShowDTO(Map.Entry<MealProjection, List<MealItemShowDTO>> entry);

    TotalMealShowDTO getTotal(List<MealShowDTO> meals);

    MealShowDTO editMeal(MealEditDTO mealEditDTO);
}

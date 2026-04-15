package org.kane.database.repository.meal;

import org.kane.domain.DTO.entityDTO.meal.MealProjection;

import java.util.List;

public interface CustomMealRepository {

    List<MealProjection> getMealsOfDiaryRecord(Long diaryRecord);
}

package org.kane.database.repository.meal;

import org.kane.database.entity.diary.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long>, CustomMealRepository {
}

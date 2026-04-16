package org.kane.database.repository.diary.meal_item;

import org.kane.database.entity.diary.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealItemRepository extends JpaRepository<MealItem, Long>, CustomMealItemRepository {

}

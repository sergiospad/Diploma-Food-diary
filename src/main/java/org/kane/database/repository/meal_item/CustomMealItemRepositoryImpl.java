package org.kane.database.repository.meal_item;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.meal_item.MealItemShowDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.diary.QMealItem.mealItem;

@Repository
@RequiredArgsConstructor
public class CustomMealItemRepositoryImpl implements CustomMealItemRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MealItemShowDTO> getMealItemsOfMeal(Long mealID){
       return queryFactory.select(Projections.constructor(MealItemShowDTO.class,
                    mealItem.id,
                    mealItem.nutritionalInfo.name,
                    mealItem.productWeight,
                    mealItem.nutritionalInfo.calories,
                    mealItem.nutritionalInfo.protein,
                    mealItem.nutritionalInfo.fat,
                    mealItem.nutritionalInfo.carbs))
                .from(mealItem)
                .where(mealItem.meal.id.eq(mealID))
                .fetch();
    }
}

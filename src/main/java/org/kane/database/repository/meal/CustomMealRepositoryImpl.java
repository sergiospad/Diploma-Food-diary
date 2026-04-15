package org.kane.database.repository.meal;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.kane.domain.DTO.entityDTO.meal.MealProjection;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.diary.QMeal.meal;

@Repository
@RequiredArgsConstructor
public class CustomMealRepositoryImpl implements CustomMealRepository {
    private final JPAQueryFactory queryFactory;
    private static @NonNull <T> NumberTemplate<Double> getDoubleNumberTemplate(SimplePath<T> simplePath) {
        return Expressions.numberTemplate(Double.class, "SUM({0})", simplePath);
    }

    @Override
    public List<MealProjection> getMealsOfDiaryRecord(Long diaryRecordID){
        return queryFactory.select(Projections.constructor(MealProjection.class,
                meal.id,
                meal.type))
                .from(meal)
                .where(meal.dailyRecord.id.eq(diaryRecordID))
                .fetch();
    }
}

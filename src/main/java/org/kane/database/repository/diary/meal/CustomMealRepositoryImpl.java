package org.kane.database.repository.diary.meal;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.kane.database.enum_types.NutritionType;
import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.kane.database.entity.diary.QDiaryRecord.diaryRecord;
import static org.kane.database.entity.diary.QMeal.meal;
import static org.kane.database.entity.diary.QMealItem.mealItem;

@Repository
@RequiredArgsConstructor
public class CustomMealRepositoryImpl implements CustomMealRepository {
    private final JPAQueryFactory queryFactory;
    private static @NonNull <T> NumberTemplate<Double> getDoubleNumberTemplate(SimplePath<T> simplePath) {
        return Expressions.numberTemplate(Double.class, "SUM({0})", simplePath);
    }

    @Override
    public Map<MealProjection, List<MealItemShowDTO>> getShowDTOMap(LocalDate recordDate, Long userID){
        List<Tuple> rows = queryFactory.select(
                        meal.id,
                        meal.type,
                        meal.mealTime,
                        mealItem.id,
                        mealItem.nutritionalInfo.name,
                        mealItem.nutritionalInfo.discriminator,
                        mealItem.productWeight,
                        mealItem.nutritionalInfo.calories,
                        mealItem.nutritionalInfo.protein,
                        mealItem.nutritionalInfo.fat,
                        mealItem.nutritionalInfo.carbs)
                .from(diaryRecord)
                .join(diaryRecord.meals, meal)
                .join(meal.mealItems, mealItem)
                .join(mealItem.nutritionalInfo)
                .where(diaryRecord.recordDate.eq(recordDate)
                        .and(diaryRecord.user.id.eq(userID)))
                .fetch();
        Map<MealProjection, List<MealItemShowDTO>> mealMap = new LinkedHashMap<>();
        MealProjection mealProjection;
        MealItemShowDTO mealItemShowDTO;
        for (Tuple row : rows) {
            mealProjection =MealProjection.builder()
                    .id(row.get(meal.id))
                    .type(row.get(meal.type))
                    .time(row.get(meal.mealTime))
                    .build();
            var weight =  row.get(mealItem.productWeight);
            assert weight != null;
            var coeff = weight.getWeightCoefficient();
            mealItemShowDTO = new MealItemShowDTO(row.get(mealItem.id),
                    row.get(mealItem.nutritionalInfo.name),
                    weight,
                    row.get(mealItem.nutritionalInfo.calories),
                    row.get(mealItem.nutritionalInfo.protein),
                    row.get(mealItem.nutritionalInfo.fat),
                    row.get(mealItem.nutritionalInfo.carbs));
            String disc = row.get(mealItem.nutritionalInfo.discriminator);
            if (disc != null) {
                mealItemShowDTO.setNutritionType(NutritionType.valueOf(disc));
            }
            mealItemShowDTO.getCalories().multiply(coeff);
            mealItemShowDTO.getProteins().multiply(coeff);
            mealItemShowDTO.getFat().multiply(coeff);
            mealItemShowDTO.getCarbs().multiply(coeff);
            mealMap.computeIfAbsent(mealProjection, k -> new ArrayList<>());
            mealMap.get(mealProjection).add(mealItemShowDTO);
        }
        return mealMap;
    }

    @Override
    public List<MealItemShowDTO> showMealItems(Long mealID){
        List<Tuple> rows = queryFactory.select(
                        mealItem.id,
                        mealItem.nutritionalInfo.name,
                        mealItem.nutritionalInfo.discriminator,
                        mealItem.productWeight,
                        mealItem.nutritionalInfo.calories,
                        mealItem.nutritionalInfo.protein,
                        mealItem.nutritionalInfo.fat,
                        mealItem.nutritionalInfo.carbs)
                .from(meal)
                .join(meal.mealItems, mealItem)
                .join(mealItem.nutritionalInfo)
                .where(meal.id.eq(mealID))
                .fetch();
        List<MealItemShowDTO> result = new ArrayList<>();
        for (Tuple row : rows) {
            var weight = row.get(mealItem.productWeight);
            assert weight != null;
            var coeff = weight.getWeightCoefficient();
            MealItemShowDTO mealItemShowDTO = new MealItemShowDTO(row.get(mealItem.id),
                    row.get(mealItem.nutritionalInfo.name),
                    weight,
                    row.get(mealItem.nutritionalInfo.calories),
                    row.get(mealItem.nutritionalInfo.protein),
                    row.get(mealItem.nutritionalInfo.fat),
                    row.get(mealItem.nutritionalInfo.carbs));
            String disc = row.get(mealItem.nutritionalInfo.discriminator);
            if (disc != null) {
                mealItemShowDTO.setNutritionType(NutritionType.valueOf(disc));
            }
            mealItemShowDTO.getCalories().multiply(coeff);
            mealItemShowDTO.getProteins().multiply(coeff);
            mealItemShowDTO.getFat().multiply(coeff);
            mealItemShowDTO.getCarbs().multiply(coeff);
            result.add(mealItemShowDTO);
        }
        return result;
    }
}

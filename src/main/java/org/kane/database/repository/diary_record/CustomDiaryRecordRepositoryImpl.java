package org.kane.database.repository.diary_record;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.daily_record.AutocalcAndIDProjection;
import org.kane.domain.DTO.entityDTO.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.meal_item.MealItemShowDTO;
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
public class CustomDiaryRecordRepositoryImpl implements CustomDiaryRecordRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long getDiaryRecordIDByRecordDate(LocalDate recordDate, Long userID) {
        return queryFactory.select(diaryRecord.id)
                .from(diaryRecord)
                .where(diaryRecord.recordDate.eq(recordDate)
                        .and(diaryRecord.user.id.eq(userID)))
                .fetchOne();
    }

    @Override
    public Map<MealProjection, List<MealItemShowDTO>> getShowDTOMap(LocalDate recordDate, Long userID){
        List<Tuple> rows = queryFactory.select(
                    meal.id,
                    meal.type,
                    mealItem.id,
                    mealItem.nutritionalInfo.name,
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
            mealItemShowDTO.getCalories().multiply(coeff);
            mealItemShowDTO.getProteins().multiply(coeff);
            mealItemShowDTO.getFat().multiply(coeff);
            mealItemShowDTO.getCarbs().multiply(coeff);
            if(!mealMap.containsKey(mealProjection))
                mealMap.put(mealProjection, new ArrayList<>());
            mealMap.get(mealProjection).add(mealItemShowDTO);
        }
        return mealMap;
    }

    @Override
    public AutocalcAndIDProjection getIDAndAutocalc(LocalDate recordDate, Long userID){
        return queryFactory.select(Projections.constructor(AutocalcAndIDProjection.class,
                    diaryRecord.id,
                    diaryRecord.autoCalculation
                )).from(diaryRecord)
                .where(diaryRecord.recordDate.eq(recordDate)
                        .and(diaryRecord.user.id.eq(userID)))
                .fetchOne();
    }
}

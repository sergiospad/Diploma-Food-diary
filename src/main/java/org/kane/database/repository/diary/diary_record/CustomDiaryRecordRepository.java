package org.kane.database.repository.diary.diary_record;

import org.kane.database.entity.diary.DiaryRecord;
import org.kane.domain.DTO.entityDTO.diary.daily_record.AutocalcAndIDProjection;
import org.kane.domain.DTO.entityDTO.diary.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemShowDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CustomDiaryRecordRepository {
    DiaryRecord getDiaryRecordByRecordDate(LocalDate recordDate, Long userID);

    AutocalcAndIDProjection getIDAndAutocalc(LocalDate recordDate, Long userID);
}

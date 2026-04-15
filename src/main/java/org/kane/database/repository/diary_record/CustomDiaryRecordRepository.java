package org.kane.database.repository.diary_record;

import org.kane.domain.DTO.entityDTO.daily_record.AutocalcAndIDProjection;
import org.kane.domain.DTO.entityDTO.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.meal_item.MealItemShowDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface CustomDiaryRecordRepository {
    Long getDiaryRecordIDByRecordDate(LocalDate recordDate, Long userID);

    Map<MealProjection, List<MealItemShowDTO>> getShowDTOMap(LocalDate recordDate, Long userID);

    AutocalcAndIDProjection getIDAndAutocalc(LocalDate recordDate, Long userID);
}

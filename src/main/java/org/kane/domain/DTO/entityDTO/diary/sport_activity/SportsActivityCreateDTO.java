package org.kane.domain.DTO.entityDTO.diary.sport_activity;

import lombok.Data;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

import java.time.LocalDate;

@Data
public class SportsActivityCreateDTO {
    private String name;
    private Calories calories;
    private LocalDate diaryRecordDate;
}

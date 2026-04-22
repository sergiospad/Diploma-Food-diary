package org.kane.domain.DTO.entityDTO.diary.sport_activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SportsActivityCreateDTO {
    private String name;
    private Calories calories;
    private LocalDate diaryRecordDate;
}

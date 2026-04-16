package org.kane.domain.DTO.entityDTO.diary.sport_activity;

import lombok.Data;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

@Data
public class SportActivityEditDTO {
    private Long id;
    private String name;
    private Calories calories;
}

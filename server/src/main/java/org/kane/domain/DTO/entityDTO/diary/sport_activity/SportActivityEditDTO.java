package org.kane.domain.DTO.entityDTO.diary.sport_activity;

import lombok.*;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SportActivityEditDTO {
    private Long id;
    private String name;
    private Calories calories;
}

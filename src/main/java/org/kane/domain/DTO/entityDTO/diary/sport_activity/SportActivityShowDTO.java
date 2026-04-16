package org.kane.domain.DTO.entityDTO.diary.sport_activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SportActivityShowDTO {
    private Long id;
    private String name;
    private Calories calories;
}

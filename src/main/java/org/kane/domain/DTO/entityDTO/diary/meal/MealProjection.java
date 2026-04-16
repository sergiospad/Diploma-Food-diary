package org.kane.domain.DTO.entityDTO.diary.meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.MealType;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealProjection {
    Long id;
    MealType type;
    LocalTime time;
}

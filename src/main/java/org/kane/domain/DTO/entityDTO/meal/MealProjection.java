package org.kane.domain.DTO.entityDTO.meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.MealType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealProjection {
    Long id;
    MealType type;
}

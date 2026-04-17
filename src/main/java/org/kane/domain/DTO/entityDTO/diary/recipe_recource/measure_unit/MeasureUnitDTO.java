package org.kane.domain.DTO.entityDTO.diary.recipe_recource.measure_unit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.recipe_recource.MeasureUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasureUnitDTO {
    private Long id;
    private String name;
}

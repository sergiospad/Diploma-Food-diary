package org.kane.domain.DTO.entityDTO.diary.recipe_recource.measure_unit;

import lombok.Builder;
import lombok.Data;
import org.kane.database.entity.recipe_recource.MeasureUnit;

@Data
@Builder
public class MeasureUnitDTO {
    private Long id;
    private String name;
}

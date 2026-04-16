package org.kane.domain.DTO.entityDTO.diary.recipe_recource.coefficient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CoefficientShowDTO {
    Long id;
    String measureUnitName;
    Double conversionFactor;
}

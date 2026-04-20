package org.kane.domain.DTO.entityDTO.recipe_recource.coefficient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoefficientCreateDTO {
    private Long measureUnitId;
    private Double conversionFactor;
}

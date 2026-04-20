package org.kane.domain.DTO.entityDTO.diary.recipe_recource.coefficient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoefficientEditDTO {
    private Long id;
    private Long categoryID;
    private Double conversionFactor;
    private Long measureUnitID;
}

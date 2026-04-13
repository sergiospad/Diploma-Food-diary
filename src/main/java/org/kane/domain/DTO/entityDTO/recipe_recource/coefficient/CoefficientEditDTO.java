package org.kane.domain.DTO.entityDTO.recipe_recource.coefficient;

import lombok.Data;

@Data
public class CoefficientEditDTO {
    private Long id;
    private Long categoryID;
    private Double conversionFactor;
    private Long measureUnitID;
}

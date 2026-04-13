package org.kane.domain.DTO.entityDTO.coefficient;

import lombok.Data;

@Data
public class CoefficientCreateDTO {
    private Long categoryId;
    private Long measureUnitId;
    private Double conversionFactor;
}

package org.kane.domain.mappers.coefficient;

import org.kane.database.entity.recipe_recource.Coefficient;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientShowDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CoefficientMapperShow implements Mapper<Coefficient, CoefficientShowDTO> {
    @Override
    public CoefficientShowDTO map(Coefficient from) {
        return CoefficientShowDTO.builder()
                .id(from.getId())
                .measureUnitName(from.getMeasureUnit().getName())
                .conversionFactor(from.getConversionFactor())
                .build();
    }
}

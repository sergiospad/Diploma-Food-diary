package org.kane.domain.mappers;

import org.kane.database.entity.recipe_recource.Coefficient;
import org.kane.domain.DTO.entityDTO.coefficient.CoefficientShowDTO;
import org.springframework.stereotype.Component;

@Component
public class CoefficientMapperShow implements Mapper<Coefficient, CoefficientShowDTO>{
    @Override
    public CoefficientShowDTO map(Coefficient from) {
        return CoefficientShowDTO.builder()
                .id(from.getId())
                .measureUnitName(from.getMeasureUnit().getName())
                .conversionFactor(from.getConversionFactor())
                .build();
    }
}

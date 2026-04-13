package org.kane.domain.service.recipe_recource.coefficient;

import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientEditDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientShowDTO;

public interface CoefficientService{
    CoefficientShowDTO addCoefficient(CoefficientCreateDTO coefficientShowDTO);

    CoefficientShowDTO editCoefficient(CoefficientEditDTO coefficientEditDTO);
}

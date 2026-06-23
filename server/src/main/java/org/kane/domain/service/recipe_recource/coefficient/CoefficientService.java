package org.kane.domain.service.recipe_recource.coefficient;

import org.kane.database.entity.recipe_recource.Category;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientEditDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientShowDTO;

public interface CoefficientService{
    CoefficientShowDTO addCoefficient(CoefficientCreateDTO coefficientShowDTO, Category category);

    CoefficientShowDTO editCoefficient(CoefficientEditDTO coefficientEditDTO);
}

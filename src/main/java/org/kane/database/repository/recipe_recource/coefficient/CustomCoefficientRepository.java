package org.kane.database.repository.recipe_recource.coefficient;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.coefficient.CoefficientShowDTO;

import java.util.List;

public interface CustomCoefficientRepository {
    Double getCoefficientByProductID(Long productID, Long measureUnitID);

    List<CoefficientShowDTO> getShowDTOByCategoryID(Long categoryID);
}

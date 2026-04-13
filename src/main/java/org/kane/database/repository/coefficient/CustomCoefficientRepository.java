package org.kane.database.repository.coefficient;

import org.kane.domain.DTO.entityDTO.coefficient.CoefficientShowDTO;

import java.util.List;

public interface CustomCoefficientRepository {
    Double getCoefficientByProductID(Long productID, Long measureUnitID);

    List<CoefficientShowDTO> getShowDTOByCategoryID(Long categoryID);
}

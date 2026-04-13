package org.kane.domain.service.coefficient;

import org.kane.domain.DTO.entityDTO.coefficient.CoefficientCreateDTO;
import org.kane.domain.DTO.entityDTO.coefficient.CoefficientEditDTO;
import org.kane.domain.DTO.entityDTO.coefficient.CoefficientShowDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface CoefficientService{
    CoefficientShowDTO addCoefficient(CoefficientCreateDTO coefficientShowDTO);

    CoefficientShowDTO editCoefficient(CoefficientEditDTO coefficientEditDTO);
}

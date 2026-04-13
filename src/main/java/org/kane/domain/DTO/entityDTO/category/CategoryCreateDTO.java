package org.kane.domain.DTO.entityDTO.category;

import lombok.Data;
import org.kane.domain.DTO.entityDTO.coefficient.CoefficientCreateDTO;

import java.util.List;

@Data
public class CategoryCreateDTO {
    private String name;
    private List<CoefficientCreateDTO> coefficients;
}

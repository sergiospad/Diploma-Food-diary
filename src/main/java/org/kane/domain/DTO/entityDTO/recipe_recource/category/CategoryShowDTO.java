package org.kane.domain.DTO.entityDTO.recipe_recource.category;

import lombok.Builder;
import lombok.Data;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientShowDTO;

import java.util.List;

@Data
@Builder
public class CategoryShowDTO {
    Long id;
    String name;
    List<CoefficientShowDTO> coefficients;
}

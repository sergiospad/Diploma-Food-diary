package org.kane.domain.DTO.entityDTO.recipe_recource.coefficient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryAddCoefficientDTO {
    Long id;
    List<CoefficientCreateDTO> coefficients;
}

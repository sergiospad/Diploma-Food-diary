package org.kane.domain.DTO.entityDTO.recipe_recource.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientCreateDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDTO {
    private String name;
    private List<CoefficientCreateDTO> coefficients;
}

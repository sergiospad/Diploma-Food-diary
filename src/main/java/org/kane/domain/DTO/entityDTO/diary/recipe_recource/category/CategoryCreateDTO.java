package org.kane.domain.DTO.entityDTO.diary.recipe_recource.category;

import lombok.Data;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.coefficient.CoefficientCreateDTO;

import java.util.List;

@Data
public class CategoryCreateDTO {
    private String name;
    private List<CoefficientCreateDTO> coefficients;
}

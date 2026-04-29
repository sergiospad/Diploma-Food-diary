package org.kane.domain.DTO.entityDTO.recipe_recource.ingredient;

import lombok.Builder;
import lombok.Data;
import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;

import java.util.List;

@Data
@Builder
public class IngredientShowDTO {
    private Long id;
    private String productName;
    private Double amount;
    private List<MeasureUnitDTO> units;
}

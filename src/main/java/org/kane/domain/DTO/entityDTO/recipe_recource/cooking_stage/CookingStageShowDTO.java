package org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CookingStageShowDTO {
    Long id;
    Short stageNumber;
    String description;
    Long imageId;
}

package org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookingStageCreateDTO {
    private String description;
    private Short stageNumber;
    private Long imageID;
}

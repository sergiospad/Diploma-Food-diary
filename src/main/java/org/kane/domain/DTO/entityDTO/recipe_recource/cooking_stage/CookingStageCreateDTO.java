package org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage;

import lombok.Data;

@Data
public class CookingStageCreateDTO {
    private String description;
    private Short stageNumber;
    private Long imageID;
}

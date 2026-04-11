package org.kane.domain.DTO.entityDTO.cooking_stage;

import lombok.Data;

@Data
public class CookingStageCreateDTO {
    private String description;
    private Short stageNumber;
    private Long imageID;
}

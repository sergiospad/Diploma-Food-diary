package org.kane.domain.DTO.entityDTO.cooking_stage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CookingStageShowDTO {
    Long id;
    Short stageNumber;
    String description;
    Long imageId;
}

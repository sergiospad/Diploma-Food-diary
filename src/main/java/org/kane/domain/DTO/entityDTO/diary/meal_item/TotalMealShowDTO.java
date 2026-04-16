package org.kane.domain.DTO.entityDTO.diary.meal_item;

import lombok.*;
import org.kane.domain.DTO.entityDTO.diary.meal.NutritionalInfoDTO;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class TotalMealShowDTO extends NutritionalInfoDTO {
    public TotalMealShowDTO() {
        super();
    }


}

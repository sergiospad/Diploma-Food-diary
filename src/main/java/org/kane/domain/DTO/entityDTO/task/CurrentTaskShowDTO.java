package org.kane.domain.DTO.entityDTO.task;

import lombok.Builder;
import lombok.Data;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.TaskTarget;

@Data
@Builder
public class CurrentTaskShowDTO {
    private Long id;
    private HumanWeight startWeight;
    private HumanWeight targetWeight;
    private HumanWeight currentWeight;
    private Short numberOfDays;
    private TaskTarget taskTarget;
    private Calories caloriesDeficit;
}

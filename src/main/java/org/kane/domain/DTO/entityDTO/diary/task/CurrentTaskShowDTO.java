package org.kane.domain.DTO.entityDTO.diary.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.TaskStatus;
import org.kane.database.enum_types.TaskTarget;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentTaskShowDTO {
    private Long id;
    private HumanWeight startWeight;
    private HumanWeight targetWeight;
    private HumanWeight currentWeight;
    private Short numberOfDays;
    private TaskTarget taskTarget;
    private Calories caloriesDeficit;
    private TaskStatus taskStatus;
}

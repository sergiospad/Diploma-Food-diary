package org.kane.domain.DTO.entityDTO.task;

import lombok.Data;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.TaskTarget;

import java.time.LocalDate;

@Data
public class TaskCreateDTO {
    private TaskTarget target;
    private HumanWeight targetWeight;
    private Calories caloriesDeficit;
    private LocalDate beginningDate;
}

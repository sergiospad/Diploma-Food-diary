package org.kane.domain.DTO.entityDTO.diary.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.TaskTarget;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskCreateDTO {
    private TaskTarget target;
    private HumanWeight targetWeight;
    private Calories caloriesDeficit;
    private LocalDate beginningDate;
}

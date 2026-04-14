package org.kane.domain.DTO.entityDTO.task;

import lombok.Data;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.TaskStatus;
import org.kane.database.enum_types.TaskTarget;

import java.time.LocalDate;

@Data
public class TaskArchiveShowDTO {
    Long id;
    Calories caloriesDeficit;
    TaskTarget currentTarget;
    LocalDate beginningDate;
    LocalDate endingDate;
    TaskStatus status;
}

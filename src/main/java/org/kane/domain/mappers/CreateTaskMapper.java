package org.kane.domain.mappers;

import org.kane.database.entity.diary.Task;
import org.kane.database.enum_types.TaskStatus;
import org.kane.domain.DTO.entityDTO.diary.task.TaskCreateDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateTaskMapper implements Mapper<TaskCreateDTO, Task> {
    @Override
    public Task map(TaskCreateDTO from) {
        return Task.builder()
                .beginningDate(from.getBeginningDate())
                .caloriesDeficit(from.getCaloriesDeficit())
                .target(from.getTarget())
                .status(TaskStatus.PLANNED)
                .targetWeight(from.getTargetWeight())
                .build();
    }
}

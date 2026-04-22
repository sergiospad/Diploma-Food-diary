package org.kane.domain.mappers.task;

import org.kane.database.entity.diary.Task;
import org.kane.domain.DTO.entityDTO.diary.task.CurrentTaskShowDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CurrentTaskMapper implements Mapper<Task, CurrentTaskShowDTO> {
    @Override
    public CurrentTaskShowDTO map(Task from) {
        return CurrentTaskShowDTO.builder()
                .id(from.getId())
                .targetWeight(from.getTargetWeight())
                .taskTarget(from.getTarget())
                .caloriesDeficit(from.getCaloriesDeficit())
                .taskStatus(from.getStatus())
                .build();
    }
}

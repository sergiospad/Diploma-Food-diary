package org.kane.database.repository.task;

import org.kane.database.entity.diary.Task;
import org.kane.domain.DTO.entityDTO.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.task.TaskArchiveShowDTO;

import java.util.List;

public interface CustomTaskRepository {
    Task getLastTask(Long userID);
    List<TaskArchiveShowDTO> showArchives(Long userID);
}

package org.kane.database.repository.diary.task;

import org.kane.database.entity.diary.Task;
import org.kane.domain.DTO.entityDTO.diary.task.TaskArchiveShowDTO;

import java.util.List;

public interface CustomTaskRepository {
    Task getLastTask(Long userID);
    List<TaskArchiveShowDTO> showArchives(Long userID);
}

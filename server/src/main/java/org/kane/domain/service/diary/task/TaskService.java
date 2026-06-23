package org.kane.domain.service.diary.task;

import org.kane.domain.DTO.entityDTO.diary.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskArchiveShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskCreateDTO;
import org.kane.domain.DTO.request.ChangeStatusTaskRequest;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

public interface TaskService {

    CurrentTaskShowDTO createTask(Principal principal, TaskCreateDTO createDTO);

    CurrentTaskShowDTO getCurrentTask(Principal principal);

    void changeStatusOfTask(ChangeStatusTaskRequest changeStatusTaskRequest);

    @Transactional
    List<TaskArchiveShowDTO> getArchiveTasks(Principal principal);
}

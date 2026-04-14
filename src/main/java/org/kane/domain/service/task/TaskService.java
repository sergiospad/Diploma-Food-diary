package org.kane.domain.service.task;

import org.kane.database.entity.diary.Task;
import org.kane.domain.DTO.entityDTO.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.task.TaskCreateDTO;
import org.kane.domain.DTO.request.ChangeStatusTaskRequest;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

public interface TaskService {

    CurrentTaskShowDTO createTask(Principal principal, TaskCreateDTO createDTO);

    CurrentTaskShowDTO getCurrentTask(Principal principal);

    void changeStatusOfTask(ChangeStatusTaskRequest changeStatusTaskRequest);
}

package org.kane.domain.service.diary.task;

import org.kane.domain.DTO.entityDTO.diary.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskCreateDTO;
import org.kane.domain.DTO.request.ChangeStatusTaskRequest;

import java.security.Principal;

public interface TaskService {

    CurrentTaskShowDTO createTask(Principal principal, TaskCreateDTO createDTO);

    CurrentTaskShowDTO getCurrentTask(Principal principal);

    void changeStatusOfTask(ChangeStatusTaskRequest changeStatusTaskRequest);
}

package org.kane.web.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.diary.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskArchiveShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskCreateDTO;
import org.kane.domain.DTO.request.ChangeStatusTaskRequest;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.diary.task.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CurrentTaskShowDTO> createTask(Principal principal, @RequestBody TaskCreateDTO createDTO){
        var result  = taskService.createTask(principal, createDTO);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CurrentTaskShowDTO> getCurrentTask(Principal principal){
        var result = taskService.getCurrentTask(principal);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/changeStatus")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> changeStatus(@RequestBody ChangeStatusTaskRequest changeStatusTaskRequest){
        taskService.changeStatusOfTask(changeStatusTaskRequest);
        return ResponseEntity.ok(new MessageResponse("Status changed"));
    }

    @GetMapping("/archive")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<TaskArchiveShowDTO>> getArchiveTasks(Principal principal){
        var result = taskService.getArchiveTasks(principal);
        return ResponseEntity.ok().body(result);
    }
}

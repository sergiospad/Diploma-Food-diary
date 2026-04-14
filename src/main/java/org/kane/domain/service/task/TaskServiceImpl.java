package org.kane.domain.service.task;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.Task;
import org.kane.database.enum_types.TaskStatus;
import org.kane.database.repository.task.TaskRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.database.repository.weight_record.WeightRecordRepository;
import org.kane.domain.DTO.entityDTO.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.task.TaskCreateDTO;
import org.kane.domain.DTO.request.ChangeStatusTaskRequest;
import org.kane.domain.mappers.CreateTaskMapper;
import org.kane.exceptions.not_found.TaskNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final CreateTaskMapper createTaskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final WeightRecordRepository weightRecordRepository;


    @Transactional
    @Override
    public CurrentTaskShowDTO createTask(Principal principal, TaskCreateDTO createDTO){
        var user = userRepository.getCurrentUser(principal);
        var task = createTaskMapper.map(createDTO);
        task.setUser(user);
        if(task.getStatus() == TaskStatus.ONGOING) {
            task.setStartWeightRecord(weightRecordRepository.getLastMeasurement(user.getId()));
            task = taskRepository.save(task);
            return buildOngoingTask(task, user.getId());
        }
        return buildPlannedShowTask(task);
    }

    @Override
    @Transactional
    public CurrentTaskShowDTO getCurrentTask(Principal principal) {
        var userId = userRepository.getCurrentUserId(principal);
        var task = taskRepository.getLastTask(userId);
        if(task == null) return null;
        var status = task.getStatus();
        if(status == TaskStatus.COMPLETED || status ==TaskStatus.ABORTED) return null;
        if(status == TaskStatus.PLANNED){
            if(task.getBeginningDate().isBefore(LocalDate.now())
                    ||task.getBeginningDate().equals(LocalDate.now())){
                task.setStatus(TaskStatus.ONGOING);
                task.setStartWeightRecord(weightRecordRepository.getLastMeasurement(userId));
                taskRepository.save(task);
            }
            else
                return buildPlannedShowTask(task);
        }
        return buildOngoingTask(task, userId);
    }

    private CurrentTaskShowDTO buildPlannedShowTask(Task task) {
        return CurrentTaskShowDTO.builder()
                .id(task.getId())
                .startWeight(null)
                .targetWeight(task.getTargetWeight())
                .currentWeight(null)
                .numberOfDays((short) 0)
                .taskTarget(task.getTarget())
                .caloriesDeficit(task.getCaloriesDeficit())
                .build();
    }

    private CurrentTaskShowDTO buildOngoingTask(Task task, Long userId) {
        return CurrentTaskShowDTO.builder()
                .id(task.getId())
                .startWeight(task.getStartWeightRecord().getMeasuredWeight())
                .targetWeight(task.getTargetWeight())
                .currentWeight(weightRecordRepository.getLastMeasurement(userId).getMeasuredWeight())
                .numberOfDays((short) (Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), task.getBeginningDate())) + 1))
                .taskTarget(task.getTarget())
                .caloriesDeficit(task.getCaloriesDeficit())
                .build();
    }

    @Override
    @Transactional
    public void changeStatusOfTask(ChangeStatusTaskRequest changeStatusTaskRequest){
        var task = taskRepository.findById(changeStatusTaskRequest.getId())
                .orElseThrow(()->new TaskNotFoundException("Task not found"));
        task.setStatus(changeStatusTaskRequest.getStatus());
        task.setEndingDate(changeStatusTaskRequest.getEndingDate());
        taskRepository.save(task);
    }


}

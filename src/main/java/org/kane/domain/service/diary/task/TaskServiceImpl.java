package org.kane.domain.service.diary.task;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.Task;
import org.kane.database.enum_types.TaskStatus;
import org.kane.database.repository.diary.task.TaskRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.database.repository.diary.weight_record.WeightRecordRepository;
import org.kane.domain.DTO.entityDTO.diary.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskArchiveShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskCreateDTO;
import org.kane.domain.DTO.request.ChangeStatusTaskRequest;
import org.kane.domain.mappers.CreateTaskMapper;
import org.kane.domain.mappers.CurrentTaskMapper;
import org.kane.exceptions.not_found.TaskNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final CreateTaskMapper createTaskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final WeightRecordRepository weightRecordRepository;
    private final CurrentTaskMapper currentTaskMapper;


    @Transactional
    @Override
    public CurrentTaskShowDTO createTask(Principal principal, TaskCreateDTO createDTO){
        var user = userRepository.getCurrentUser(principal);
        var task = createTaskMapper.map(createDTO);
        task.setUser(user);
        var lastWeight = weightRecordRepository.getLastMeasurement(user.getId());
        if(task.getBeginningDate().isAfter(LocalDate.now())){
            task.setStatus(TaskStatus.PLANNED);
        }
        else if(task.getBeginningDate().isEqual(LocalDate.now())
                || task.getBeginningDate().isBefore(LocalDate.now())){
            task.setStatus(TaskStatus.ONGOING);
        }
        task.setStartWeightRecord(lastWeight);
        task = taskRepository.save(task);
        if (task.getStatus() == TaskStatus.ONGOING) {
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
        return currentTaskMapper.map(task);
    }

    private CurrentTaskShowDTO buildOngoingTask(Task task, Long userId) {
        var currentTask = currentTaskMapper.map(task);
        currentTask.setStartWeight(task.getStartWeightRecord().getMeasuredWeight());
        currentTask.setNumberOfDays((short) (Math.abs(ChronoUnit.DAYS.between(LocalDate.now(), task.getBeginningDate())) + 1));
        currentTask.setCurrentWeight(weightRecordRepository.getLastMeasurement(userId).getMeasuredWeight());
        return currentTask;
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

    @Transactional
    @Override
    public List<TaskArchiveShowDTO> getArchiveTasks(Principal principal){
        var userId = userRepository.getCurrentUserId(principal);
        return taskRepository.showArchives(userId);
    }


}

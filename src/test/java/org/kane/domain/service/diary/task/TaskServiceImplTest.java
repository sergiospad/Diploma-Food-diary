package org.kane.domain.service.diary.task;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.TaskStatus;
import org.kane.database.enum_types.TaskTarget;
import org.kane.database.repository.diary.task.TaskRepository;
import org.kane.domain.DTO.entityDTO.diary.task.TaskArchiveShowDTO;
import org.kane.domain.DTO.entityDTO.diary.task.TaskCreateDTO;
import org.kane.domain.DTO.request.ChangeStatusTaskRequest;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/diary/data-task-test-repsoitory.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class TaskServiceImplTest extends IntegrationTestServiceBase {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    void createTask() {
        Principal principal = ()-> "jane_smith";
        var tcdto = TaskCreateDTO.builder()
                .target(TaskTarget.W_LOSS)
                .targetWeight(new HumanWeight(90))
                .caloriesDeficit(new Calories(1000.0))
                .beginningDate(LocalDate.now().plusDays(100))
                .build();
        var tsdto = taskService.createTask(principal, tcdto);
        assertThat(tsdto.getId()).isEqualTo(5L);
        assertThat(tsdto.getTaskTarget()).isEqualTo(tcdto.getTarget());
        assertThat(tsdto.getCaloriesDeficit()).isEqualTo(tcdto.getCaloriesDeficit());
        assertThat(tsdto.getTargetWeight()).isEqualTo(tcdto.getTargetWeight());
        assertThat(tsdto.getTaskStatus()).isEqualTo(TaskStatus.PLANNED);
    }

    @Test
    void createTask1() {
        Principal principal = ()-> "jane_smith";
        var tcdto = TaskCreateDTO.builder()
                .target(TaskTarget.W_LOSS)
                .targetWeight(new HumanWeight(90))
                .caloriesDeficit(new Calories(1000.0))
                .beginningDate(LocalDate.now().minusDays(100))
                .build();
        var tsdto = taskService.createTask(principal, tcdto);
        assertThat(tsdto.getId()).isEqualTo(5L);
        assertThat(tsdto.getTaskTarget()).isEqualTo(tcdto.getTarget());
        assertThat(tsdto.getCaloriesDeficit()).isEqualTo(tcdto.getCaloriesDeficit());
        assertThat(tsdto.getTargetWeight()).isEqualTo(tcdto.getTargetWeight());
        assertThat(tsdto.getTaskStatus()).isEqualTo(TaskStatus.ONGOING);
        assertThat(tsdto.getNumberOfDays()).isEqualTo((short) 101);
        assertThat(tsdto.getCurrentWeight()).isEqualTo(new HumanWeight(64.1));
    }


    @Test
    void getCurrentTask() {
        Principal principal = () -> "john_doe";
        var currentTask = taskService.getCurrentTask(principal);
        assertThat(currentTask.getId()).isEqualTo(1L);
        assertThat(currentTask.getCurrentWeight()).isEqualTo(new HumanWeight(83.0));
        assertThat(currentTask.getTaskStatus()).isEqualTo(TaskStatus.ONGOING);
        assertThat(currentTask.getTaskTarget()).isEqualTo(TaskTarget.W_LOSS);
        assertThat(currentTask.getCaloriesDeficit()).isEqualTo(new  Calories(500.0));
        assertThat(currentTask.getTargetWeight()).isEqualTo(new  HumanWeight(75.0));
    }

    @Transactional
    @Test
    void changeStatusOfTask() {
        var cstr = ChangeStatusTaskRequest.builder()
                .id(1L)
                .status(TaskStatus.ABORTED)
                .endingDate(LocalDate.now())
                .build();
        var task = taskRepository.findById(cstr.getId()).orElseThrow();
        assertThat(task.getStatus()).isNotEqualTo(TaskStatus.ABORTED);
        assertThat(task.getEndingDate()).isNotEqualTo(LocalDate.now());
        taskService.changeStatusOfTask(cstr);
        assertThat(task.getStatus()).isEqualTo(TaskStatus.ABORTED);
        assertThat(task.getEndingDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void getArchiveTasks(){
        Principal principal = ()-> "john_doe";
        var list = taskService.getArchiveTasks(principal);
        assertThat(list).hasSize(2);
        assertThat(list.stream().map(TaskArchiveShowDTO::getId).toList()).containsExactlyInAnyOrder(1L, 4L);
    }
}
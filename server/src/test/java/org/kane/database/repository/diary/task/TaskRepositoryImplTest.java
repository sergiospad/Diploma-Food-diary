package org.kane.database.repository.diary.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.User;
import org.kane.database.entity.diary.Task;
import org.kane.database.entity.diary.WeightRecord;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.TaskStatus;
import org.kane.database.enum_types.TaskTarget;
import org.kane.domain.DTO.entityDTO.diary.task.TaskArchiveShowDTO;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
class TaskRepositoryImplTest extends IntegrationTestBase {

    private User savedUser;
    private WeightRecord savedWeightRecord;
    private List<Task> savedTasks;
    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        savedUser = savedEntities.getUser();
        savedWeightRecord = WeightRecord.builder()
                .id(3L)
                .measuredWeight(new HumanWeight(83.00))
                .dateOfMeasurement(LocalDate.of(2024, 1, 15))
                .user(savedUser)
                .build();
        var task = Task.builder().id(1L)
                .beginningDate(LocalDate.of(2025, 1,1))
                .caloriesDeficit(new Calories(500.0))
                .target(TaskTarget.W_LOSS)
                .status(TaskStatus.ONGOING)
                .targetWeight(new HumanWeight(75.0))
                .startWeightRecord(savedWeightRecord)
                .user(savedUser)
                .build();
        savedTasks = new ArrayList<>();
        savedTasks.add(task);
        task = Task.builder().id(4L)
                .beginningDate(LocalDate.of(2023, 10,1))
                .endingDate(LocalDate.of(2023, 12,31))
                .caloriesDeficit(new Calories(400.0))
                .target(TaskTarget.W_LOSS)
                .status(TaskStatus.COMPLETED)
                .targetWeight(new HumanWeight(72.0))
                .startWeightRecord(savedWeightRecord)
                .user(savedUser)
                .build();
        savedTasks.add(task);
    }

    @Test
    void getLastTask() {
        var lastTask = taskRepository.getLastTask(savedUser.getId());
        assertThat(lastTask).isNotNull();
        var savedTask = savedTasks.getFirst();
        System.out.println(savedTask);
        assertThat(lastTask.getId()).as("Id").isEqualTo(savedTask.getId());
        assertThat(lastTask.getBeginningDate()).isEqualTo(savedTask.getBeginningDate());
        assertThat(lastTask.getEndingDate()).isEqualTo(savedTask.getEndingDate());
        assertThat(lastTask.getUser().getId()).isEqualTo(savedTask.getUser().getId());
        assertThat(lastTask.getCaloriesDeficit()).isEqualTo(savedTask.getCaloriesDeficit());
        assertThat(lastTask.getTarget()).isEqualTo(savedTask.getTarget());
        assertThat(lastTask.getStatus()).isEqualTo(savedTask.getStatus());
        assertThat(lastTask.getStartWeightRecord().getId()).as("Weight Record").isEqualTo(savedTask.getStartWeightRecord().getId());
        assertThat(lastTask.getTargetWeight()).isEqualTo(savedTask.getTargetWeight());
    }

    @Test
    void showArchives() {
        var archivedTasks = taskRepository.showArchives(savedUser.getId());
        assertThat(archivedTasks).isNotEmpty().hasSize(2);
        var tasksID = archivedTasks.stream().map(TaskArchiveShowDTO::getId).toList();
        assertThat(tasksID).containsAll(savedTasks.stream().map(Task::getId).toList());
        var tasksCalories = archivedTasks.stream().map(TaskArchiveShowDTO::getCaloriesDeficit).toList();
        assertThat(tasksCalories).containsAll(savedTasks.stream().map(Task::getCaloriesDeficit).toList());
        var tasksTargets = archivedTasks.stream().map(TaskArchiveShowDTO::getCurrentTarget).toList();
        assertThat(tasksTargets).containsAll(savedTasks.stream().map(Task::getTarget).toList());
        var tasksBegDate = archivedTasks.stream().map(TaskArchiveShowDTO::getBeginningDate).toList();
        assertThat(tasksBegDate).containsAll(savedTasks.stream().map(Task::getBeginningDate).toList());
        var tasksEndingDate = archivedTasks.stream().map(TaskArchiveShowDTO::getEndingDate).toList();
        assertThat(tasksEndingDate).containsAll(savedTasks.stream().map(Task::getEndingDate).toList());
        var tasksStatus = archivedTasks.stream().map(TaskArchiveShowDTO::getStatus).toList();
        assertThat(tasksStatus).containsAll(savedTasks.stream().map(Task::getStatus).toList());
    }
}
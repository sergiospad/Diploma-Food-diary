package org.kane.database.repository.task;

import org.kane.database.entity.diary.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, CustomTaskRepository {
}

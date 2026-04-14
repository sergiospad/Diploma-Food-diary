package org.kane.database.repository.task;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.Task;
import org.kane.domain.DTO.entityDTO.task.CurrentTaskShowDTO;
import org.kane.domain.DTO.entityDTO.task.TaskArchiveShowDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QUser.user;
import static org.kane.database.entity.diary.QTask.task;

@Repository
@RequiredArgsConstructor
public class CustomTaskRepositoryImpl implements CustomTaskRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Task getLastTask(Long userID) {
        return queryFactory.select(task)
                .from(user)
                .join(user.tasks, task)
                .where(user.id.eq(userID))
                .orderBy(task.beginningDate.desc())
                .fetchFirst();
    }

    @Override
    public List<TaskArchiveShowDTO> showArchives(Long userID) {
        return queryFactory.select(Projections.constructor(TaskArchiveShowDTO.class,
                    task.id,
                    task.caloriesDeficit,
                    task.target,
                    task.beginningDate,
                    task.endingDate,
                    task.status))
                .from(user)
                .join(user.tasks, task)
                .where(user.id.eq(userID))
                .orderBy(task.beginningDate.desc())
                .fetch();
    }
}



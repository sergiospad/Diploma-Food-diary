package org.kane.database.repository.sport_activities;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.sport_activity.SportActivityShowDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.diary.QDiaryRecord.diaryRecord;
import static org.kane.database.entity.diary.QSportsActivity.sportsActivity;

@Repository
@RequiredArgsConstructor
public class SportsActivityRepositoryImpl implements CustomSportsActivityRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SportActivityShowDTO> getActivities(Long diaryRecordID){
        return queryFactory.select(Projections.constructor(SportActivityShowDTO.class,
                    sportsActivity.id,
                    sportsActivity.name,
                    sportsActivity.burnedCalories))
                .from(sportsActivity)
                .join(sportsActivity.diaryRecord, diaryRecord)
                .where(diaryRecord.id.eq(diaryRecordID))
                .fetch();
    }
}

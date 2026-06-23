package org.kane.database.repository.diary.diary_record;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.DiaryRecord;
import org.kane.domain.DTO.entityDTO.diary.daily_record.AutocalcAndIDProjection;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static org.kane.database.entity.diary.QDiaryRecord.diaryRecord;

@Repository
@RequiredArgsConstructor
public class CustomDiaryRecordRepositoryImpl implements CustomDiaryRecordRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public DiaryRecord getDiaryRecordByRecordDate(LocalDate recordDate, Long userID) {
        return queryFactory.select(diaryRecord)
                .from(diaryRecord)
                .where(diaryRecord.recordDate.eq(recordDate)
                        .and(diaryRecord.user.id.eq(userID)))
                .fetchOne();
    }


    @Override
    public AutocalcAndIDProjection getIDAndAutocalc(LocalDate recordDate, Long userID){
        return queryFactory.select(Projections.constructor(AutocalcAndIDProjection.class,
                    diaryRecord.id,
                    diaryRecord.autoCalculation
                )).from(diaryRecord)
                .where(diaryRecord.recordDate.eq(recordDate)
                        .and(diaryRecord.user.id.eq(userID)))
                .fetchOne();
    }

    @Override
    public Boolean diaryRecordExists(LocalDate recordDate, Long userID){
        return queryFactory.select(diaryRecord)
                .from(diaryRecord)
                .where(diaryRecord.recordDate.eq(recordDate)
                        .and(diaryRecord.user.id.eq(userID)))
                .fetchOne()!=null;
    }
}

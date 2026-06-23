package org.kane.database.repository.diary.weight_record;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.WeightRecord;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightPointDTO;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static org.kane.database.entity.QUser.user;
import static org.kane.database.entity.diary.QDiaryRecord.diaryRecord;
import static org.kane.database.entity.diary.QWeightRecord.weightRecord;

@Repository
@RequiredArgsConstructor
public class CustomWeightRecordRepositoryImpl implements CustomWeightRecordRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<WeightRecordShowDTO> findAllRecordsByUserID(Long userID){
        return queryFactory.select(Projections.constructor(WeightRecordShowDTO.class,
                    weightRecord.id,
                    weightRecord.measuredWeight,
                    weightRecord.dateOfMeasurement
                ))
                .from(weightRecord)
                .where(weightRecord.user.id.eq(userID))
                .orderBy(weightRecord.dateOfMeasurement.desc())
                .fetch();
    }

    @Override
    public WeightRecordShowDTO findLastRecordOfUser(Long userID){
        return queryFactory.select(Projections.constructor(WeightRecordShowDTO.class,
                        weightRecord.id,
                        weightRecord.measuredWeight,
                        weightRecord.dateOfMeasurement))
                .from(weightRecord)
                .where(weightRecord.user.id.eq(userID))
                .orderBy(weightRecord.dateOfMeasurement.desc())
                .fetchFirst();
    }

    @Override
    public List<WeightPointDTO> getWeightByDay(Long userId, LocalDate startDate, LocalDate endDate){
        return queryFactory.select(Projections.constructor(WeightPointDTO.class,
                        weightRecord.dateOfMeasurement,
                        weightRecord.measuredWeight))
                .from(user)
                .join(user.records, weightRecord)
                .where(user.id.eq(userId).and(weightRecord.dateOfMeasurement.between(startDate, endDate)))
                .orderBy(weightRecord.dateOfMeasurement.asc())
                .fetch();
    }

    @Override
    public List<WeightPointDTO> getWeightByWeek(Long userId, LocalDate startDate, LocalDate endDate){
        DateTemplate<LocalDate> weekStart = Expressions.dateTemplate(LocalDate.class,
                "DATE_TRUNC('week', {0})", weightRecord.dateOfMeasurement);
        return getWeightByPeriod(userId, startDate, endDate, weekStart);
    }

    @Override
    public List<WeightPointDTO> getWeightByMonth(Long userId, LocalDate startDate, LocalDate endDate){
        DateTemplate<LocalDate> monthStart = Expressions.dateTemplate(LocalDate.class,
                "DATE_TRUNC('month', {0})", weightRecord.dateOfMeasurement);
        return getWeightByPeriod(userId, startDate, endDate, monthStart);
    }

    @Override
    public List<WeightPointDTO> getWeightByYear(Long userId, LocalDate startDate, LocalDate endDate){
        DateTemplate<LocalDate> yearStart = Expressions.dateTemplate(LocalDate.class,
                "DATE_TRUNC('year', {0})", weightRecord.dateOfMeasurement);
        return getWeightByPeriod(userId, startDate, endDate, yearStart);
    }

    private List<WeightPointDTO> getWeightByPeriod(Long userId,
                                                   LocalDate startDate,
                                                   LocalDate endDate,
                                                   DateTemplate<LocalDate> period){
        NumberTemplate<Double> avgWeight = Expressions.numberTemplate(Double.class,
                "AVG({0})", weightRecord.measuredWeight);
        List<Tuple> rows = queryFactory.select(period, avgWeight)
                .from(user)
                .join(user.records, weightRecord)
                .where(user.id.eq(userId).and(weightRecord.dateOfMeasurement.between(startDate, endDate)))
                .groupBy(period)
                .orderBy(period.asc())
                .fetch();
        return rows.stream()
                .map(row -> {
                    WeightPointDTO dto = new WeightPointDTO();
                    dto.setDate(row.get(period));

                    Double avg = row.get(avgWeight);
                    dto.setWeight(avg != null ? new HumanWeight(avg) : null);

                    return dto;
                })
                .toList();
    }

    @Override
    public boolean hasAnyData(Long userId, LocalDate startDate, LocalDate endDate){
        var count = queryFactory.select(weightRecord.id.count())
                .from(user)
                .join(user.records, weightRecord)
                .where(user.id.eq(userId).and(weightRecord.dateOfMeasurement.between(startDate, endDate)))
                .fetchOne();
        if(count == null) return false;
        return count > 0;
    }

    @Override
    public WeightRecord getLastMeasurement(Long userId){
        return queryFactory.select(weightRecord)
                .from(user)
                .join(user.records, weightRecord)
                .where(user.id.eq(userId))
                .orderBy(weightRecord.dateOfMeasurement.desc())
                .fetchFirst();
    }

    @Override
    public List<LocalDate> getMeasurementDates(Long id){
        return queryFactory.select(weightRecord.dateOfMeasurement)
                .from(weightRecord)
                .where(weightRecord.user.id.eq(id))
                .orderBy(weightRecord.dateOfMeasurement.desc())
                .fetch();
    }
}

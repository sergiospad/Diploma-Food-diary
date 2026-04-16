package org.kane.database.repository.diary.weight_record;

import org.kane.database.entity.diary.WeightRecord;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightChartDataProjection;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightPointDTO;

import java.time.LocalDate;
import java.util.List;

public interface CustomWeightRecordRepository {
    List<WeightRecordShowDTO> findAllRecordsByUserID(Long userID);

    WeightRecordShowDTO findLastRecordOfUser(Long userID);

    List<WeightPointDTO> getWeightByDay(Long userId, LocalDate startDate, LocalDate endDate);

    List<WeightPointDTO> getWeightByWeek(Long userId, LocalDate startDate, LocalDate endDate);

    List<WeightPointDTO> getWeightByMonth(Long userId, LocalDate startDate, LocalDate endDate);

    List<WeightPointDTO> getWeightByYear(Long userId, LocalDate startDate, LocalDate endDate);

    WeightChartDataProjection getDataProjection(Long userId, LocalDate startDate, LocalDate endDate);

    boolean hasAnyData(Long userId, LocalDate startDate, LocalDate endDate);

    WeightRecord getLastMeasurement(Long userId);
}

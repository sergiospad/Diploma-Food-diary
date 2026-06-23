package org.kane.domain.service.diary.weight_record;

import org.kane.domain.DTO.entityDTO.diary.weight_record.CurrentWeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.MeasurementDatesDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightChartDataDTO;
import org.kane.domain.DTO.request.WeightChartRequest;

import java.security.Principal;
import java.util.List;

public interface WeightRecordService {
    List<WeightRecordShowDTO> getAllRecords(Principal principal);

    CurrentWeightRecordShowDTO getCurrentRecord(Principal principal);

    CurrentWeightRecordShowDTO createRecord(Principal principal, WeightRecordCreateDTO weightRecordCreateDTO);

    WeightChartDataDTO formChart(Principal principal, WeightChartRequest weightChartRequest);

    MeasurementDatesDTO getMeasurementDates(Principal principal);
}

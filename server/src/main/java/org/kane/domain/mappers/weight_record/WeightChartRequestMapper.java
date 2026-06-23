package org.kane.domain.mappers.weight_record;

import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightChartDataDTO;
import org.kane.domain.DTO.request.WeightChartRequest;
import org.kane.domain.mappers.CopyMapper;
import org.springframework.stereotype.Component;

@Component
public class WeightChartRequestMapper implements CopyMapper<WeightChartRequest, WeightChartDataDTO> {
    @Override
    public WeightChartDataDTO copyMap(WeightChartRequest from, WeightChartDataDTO to) {
        to.setPeriod(from.getChartPeriodType());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        return to;

    }
}

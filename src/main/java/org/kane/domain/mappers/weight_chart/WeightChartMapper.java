package org.kane.domain.mappers.weight_chart;

import org.kane.domain.DTO.entityDTO.weight_record.for_chart.WeightChartDataDTO;
import org.kane.domain.DTO.entityDTO.weight_record.for_chart.WeightChartDataProjection;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class WeightChartMapper implements Mapper<WeightChartDataProjection, WeightChartDataDTO> {
    @Override
    public WeightChartDataDTO map(WeightChartDataProjection entity) {
        return WeightChartDataDTO.builder()
                .minWeight(entity.getMinWeight())
                .maxWeight(entity.getMaxWeight())
                .avgWeight(entity.getAvgWeight())
                .build();
    }
}

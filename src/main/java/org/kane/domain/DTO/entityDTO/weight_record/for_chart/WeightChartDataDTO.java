package org.kane.domain.DTO.entityDTO.weight_record.for_chart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.enum_types.ChartPeriodType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightChartDataDTO {
    private List<WeightPointDTO> weightList;
    private ChartPeriodType period;
    private HumanWeight minWeight;
    private HumanWeight maxWeight;
    private HumanWeight avgWeight;
    private HumanWeight weightChange;
    private LocalDate startDate;
    private LocalDate endDate;
}

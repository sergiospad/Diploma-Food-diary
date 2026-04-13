package org.kane.domain.DTO.entityDTO.weight_record.for_chart;

import lombok.Data;
import org.kane.database.entity.physical_quantity.HumanWeight;

@Data
public class WeightChartDataProjection {
    private HumanWeight minWeight;
    private HumanWeight maxWeight;
    private HumanWeight avgWeight;
}

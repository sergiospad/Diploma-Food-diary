package org.kane.domain.DTO.request;

import lombok.Data;
import org.kane.database.enum_types.ChartPeriodType;

import java.time.LocalDate;

@Data
public class WeightChartRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private ChartPeriodType chartPeriodType;
}

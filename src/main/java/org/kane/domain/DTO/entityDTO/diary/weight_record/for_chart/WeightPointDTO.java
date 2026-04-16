package org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart;

import lombok.Data;
import org.kane.database.entity.physical_quantity.HumanWeight;

import java.time.LocalDate;

@Data
public class WeightPointDTO {
    private LocalDate date;
    private HumanWeight weight;
}

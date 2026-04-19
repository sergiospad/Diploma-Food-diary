package org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.HumanWeight;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightPointDTO {
    private LocalDate date;
    private HumanWeight weight;
}

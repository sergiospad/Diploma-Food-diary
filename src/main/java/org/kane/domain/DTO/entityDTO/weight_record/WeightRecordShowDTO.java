package org.kane.domain.DTO.entityDTO.weight_record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.HumanWeight;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightRecordShowDTO {
    private Long id;
    private HumanWeight measure;
    private LocalDate date;
}

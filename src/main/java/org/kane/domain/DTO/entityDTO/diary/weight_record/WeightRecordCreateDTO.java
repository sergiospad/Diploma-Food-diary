package org.kane.domain.DTO.entityDTO.diary.weight_record;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kane.database.entity.physical_quantity.HumanWeight;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeightRecordCreateDTO {
    HumanWeight measure;
    LocalDate dateOfMeasurement;
}

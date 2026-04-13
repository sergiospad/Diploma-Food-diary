package org.kane.domain.DTO.entityDTO.weight_record;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class CurrentWeightRecordShowDTO extends WeightRecordShowDTO{
    private Double BMI;
}

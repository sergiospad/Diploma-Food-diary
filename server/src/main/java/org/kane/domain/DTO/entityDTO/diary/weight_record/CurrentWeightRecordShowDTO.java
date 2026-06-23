package org.kane.domain.DTO.entityDTO.diary.weight_record;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentWeightRecordShowDTO extends WeightRecordShowDTO{
    private Double BMI;
}

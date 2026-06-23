package org.kane.domain.DTO.entityDTO.diary.weight_record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDatesDTO {
    List<LocalDate> datesOfMeasurement;
}

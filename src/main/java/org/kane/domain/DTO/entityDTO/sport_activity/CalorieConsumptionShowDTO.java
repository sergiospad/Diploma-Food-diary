package org.kane.domain.DTO.entityDTO.sport_activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalorieConsumptionShowDTO {
    Calories inRestConsumption;
    Boolean autoCalc;
    List<SportActivityShowDTO> sportActivityShowDTOList;
}

package org.kane.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kane.database.enum_types.CaloricityType;

@Data
@AllArgsConstructor
public class EnergyValueRequest {
    Long recipeID;
    CaloricityType caloricityType;
}

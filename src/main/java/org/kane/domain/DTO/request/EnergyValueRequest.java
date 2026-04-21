package org.kane.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.CaloricityType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnergyValueRequest {
    Long recipeID;
    CaloricityType caloricityType;
}

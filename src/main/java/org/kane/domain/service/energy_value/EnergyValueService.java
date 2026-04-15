package org.kane.domain.service.energy_value;

import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.domain.DTO.entityDTO.EnergyValueShowDTO;
import org.kane.domain.DTO.entityDTO.meal.MealProjection;
import org.kane.domain.DTO.entityDTO.meal.MealShowDTO;
import org.kane.domain.DTO.request.EnergyValueRequest;

public interface EnergyValueService {
//    EnergyValueShowDTO calculateAllEnergyValue(Long recipeID);
//
//    EnergyValueShowDTO calculateEnergyValuePerHundred(Long recipeID);

    EnergyValueShowDTO calculateEnergyValue(EnergyValueRequest energyValueRequest);

    Calories getBMR(Long userID);
}

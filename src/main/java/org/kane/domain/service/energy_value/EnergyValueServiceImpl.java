package org.kane.domain.service.energy_value;

import lombok.RequiredArgsConstructor;
import org.kane.database.enum_types.CaloricityType;
import org.kane.database.repository.ingredient.IngredientRepository;
import org.kane.domain.DTO.entityDTO.EnergyValueShowDTO;
import org.kane.domain.DTO.request.EnergyValueRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnergyValueServiceImpl implements EnergyValueService{


    private final IngredientRepository ingredientRepository;


    private EnergyValueShowDTO calculateAllEnergyValue(Long recipeID){
        var result = ingredientRepository.findIngredientEnergyDTOByRecipeID(recipeID).stream().
                reduce(new EnergyValueShowDTO(), EnergyValueShowDTO::merge, EnergyValueShowDTO::merge);
        result.setCaloricityType(CaloricityType.ALL);
        return result;
    }


    private EnergyValueShowDTO calculateEnergyValuePerHundred(Long recipeID){
        var enValue = calculateAllEnergyValue(recipeID);
        var coefficient = enValue.getProductWeight().getWeightCoefficient();
        enValue = enValue.divide(coefficient);
        enValue.setCaloricityType(CaloricityType.PER_HUNDRED);
        return enValue;
    }

    @Override
    public EnergyValueShowDTO calculateEnergyValue(EnergyValueRequest energyValueRequest){
        if(energyValueRequest.getCaloricityType() == CaloricityType.PER_HUNDRED)
            return calculateEnergyValuePerHundred(energyValueRequest.getRecipeID());
        return calculateAllEnergyValue(energyValueRequest.getRecipeID());
    }
}

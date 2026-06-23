package org.kane.domain.mappers;

import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.nutritional_info.EnergyValueShowDTO;
import org.springframework.stereotype.Component;

@Component
public class EnergyValueMapper implements CopyMapper<EnergyValueShowDTO, Recipe> {

    @Override
    public Recipe copyMap(EnergyValueShowDTO from, Recipe to) {
        to.setCalories(from.getCalories());
        to.setProtein(from.getProtein());
        to.setFat(from.getFat());
        to.setCarbs(from.getCarbs());
        return to;
    }
}

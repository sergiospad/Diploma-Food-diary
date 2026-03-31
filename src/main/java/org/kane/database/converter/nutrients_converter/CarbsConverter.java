package org.kane.database.converter.nutrients_converter;

import org.kane.database.entity.physical_quantity.nutrients.Carbs;

public class CarbsConverter implements BaseNutrConverter{
    @Override
    public Carbs convertToEntityAttribute(Double aDouble) {
        return new Carbs(aDouble);
    }
}

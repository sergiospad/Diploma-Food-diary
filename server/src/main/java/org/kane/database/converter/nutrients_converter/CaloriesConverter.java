package org.kane.database.converter.nutrients_converter;

import jakarta.persistence.Converter;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

@Converter(autoApply = true)
public class CaloriesConverter implements BaseNutrConverter {

    @Override
    public Calories convertToEntityAttribute(Double aDouble) {
        return new Calories(aDouble);
    }
}

package org.kane.database.converter.nutrients_converter;

import jakarta.persistence.Converter;
import org.kane.database.entity.physical_quantity.nutrients.Fat;

@Converter(autoApply = true)
public class FatConverter implements BaseNutrConverter{
    @Override
    public Fat convertToEntityAttribute(Double aDouble) {
        return new Fat(aDouble);
    }
}

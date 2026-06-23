package org.kane.database.converter.nutrients_converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.kane.database.entity.physical_quantity.nutrients.BaseNutrient;

@Converter(autoApply = true)
public interface BaseNutrConverter extends AttributeConverter<BaseNutrient,Double> {
    @Override
    public default Double convertToDatabaseColumn(BaseNutrient baseNutrient) {
        return baseNutrient == null ? null : baseNutrient.value;
    }
}

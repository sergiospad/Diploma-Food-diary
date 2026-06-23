package org.kane.database.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.kane.database.entity.physical_quantity.HumanWeight;

@Converter(autoApply = true)
public class HumanWeightConverter implements AttributeConverter<HumanWeight, Double> {
    @Override
    public Double convertToDatabaseColumn(HumanWeight humanWeight) {
        return humanWeight.value();
    }

    @Override
    public HumanWeight convertToEntityAttribute(Double aDouble) {
        return new HumanWeight(aDouble);
    }
}

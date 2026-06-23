package org.kane.database.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.kane.database.entity.physical_quantity.ProductWeight;

@Converter(autoApply = true)
public class ProductWeightConverter implements AttributeConverter<ProductWeight, Double> {
    @Override
    public Double convertToDatabaseColumn(ProductWeight attribute) {
        return attribute.getValue();
    }

    @Override
    public ProductWeight convertToEntityAttribute(Double dbData) {
        return new ProductWeight(dbData);
    }
}

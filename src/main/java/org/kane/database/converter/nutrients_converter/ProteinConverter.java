package org.kane.database.converter.nutrients_converter;

import org.kane.database.entity.physical_quantity.nutrients.Protein;

public class ProteinConverter implements BaseNutrConverter{
    @Override
    public Protein convertToEntityAttribute(Double aDouble) {
        return new Protein(aDouble);
    }
}

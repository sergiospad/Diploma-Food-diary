package org.kane.database.entity.physical_quantity.nutrients;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseNutrient {
    public final Double value;

    public abstract BaseNutrient add(BaseNutrient baseNutrient);

    public abstract BaseNutrient divide(Double coefficient);
}

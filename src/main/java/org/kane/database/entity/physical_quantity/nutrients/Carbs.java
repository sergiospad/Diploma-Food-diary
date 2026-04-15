package org.kane.database.entity.physical_quantity.nutrients;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Carbs extends BaseNutrient{
    public Carbs(Double value) {
        super(value);
    }

    @JsonCreator
    public static Carbs fromValue(Double value) {
        return new Carbs(value);
    }

}

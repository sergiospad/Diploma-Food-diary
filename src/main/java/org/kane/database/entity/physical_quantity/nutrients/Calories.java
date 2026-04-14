package org.kane.database.entity.physical_quantity.nutrients;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Calories extends BaseNutrient{

    public Calories(Double value) {
        super(value);
    }

    @JsonCreator
    public static Calories fromValue(Double value) {
        return new Calories(value);
    }

    @JsonValue
    public Double toValue() {
        return this.value;
    }

    @Override
    public Calories add(BaseNutrient baseNutrient) {
        return new Calories(this.value+ baseNutrient.value);
    }

    @Override
    public Calories divide(Double coefficient) {
        return new Calories(this.value/coefficient);
    }
}

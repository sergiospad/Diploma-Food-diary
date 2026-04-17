package org.kane.database.entity.physical_quantity.nutrients;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class Calories extends BaseNutrient{

    public Calories(Double value) {
        super(value);
    }

    @JsonCreator
    public static Calories fromValue(Double value) {
        return new Calories(value);
    }

}

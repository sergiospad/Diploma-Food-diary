package org.kane.database.entity.physical_quantity.nutrients;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Fat extends BaseNutrient{
    public Fat(Double value) {
        super(value);
    }

    @JsonCreator
    public static Fat fromValue(Double value) {
        return new Fat(value);
    }

}

package org.kane.database.entity.physical_quantity.nutrients;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class Protein extends BaseNutrient {
    public Protein(Double value) {
        super(value);
    }

    @JsonCreator
    public static Protein fromValue(Double value) {
        return new Protein(value);
    }

}

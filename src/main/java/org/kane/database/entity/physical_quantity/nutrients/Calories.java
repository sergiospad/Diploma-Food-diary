package org.kane.database.entity.physical_quantity.nutrients;

public class Calories extends BaseNutrient{

    public Calories(Double value) {
        super(value);
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

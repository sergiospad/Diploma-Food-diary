package org.kane.database.entity.physical_quantity.nutrients;

public class Carbs extends BaseNutrient{
    public Carbs(Double value) {
        super(value);
    }

    @Override
    public Carbs add(BaseNutrient baseNutrient) {
        return new Carbs(this.value+ baseNutrient.value);
    }

    @Override
    public Carbs divide(Double coefficient) {
        return new Carbs(this.value/coefficient);
    }
}

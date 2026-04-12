package org.kane.database.entity.physical_quantity.nutrients;

public class Fat extends BaseNutrient{
    public Fat(Double value) {
        super(value);
    }

    @Override
    public Fat add(BaseNutrient baseNutrient) {
        return new Fat(this.value+ baseNutrient.value);
    }

    @Override
    public Fat divide(Double coefficient) {
        return new Fat(this.value/coefficient);
    }
}

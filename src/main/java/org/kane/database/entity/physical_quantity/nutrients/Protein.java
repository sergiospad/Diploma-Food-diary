package org.kane.database.entity.physical_quantity.nutrients;

public class Protein extends BaseNutrient {
    public Protein(Double value) {
        super(value);
    }

    @Override
    public Protein add(BaseNutrient baseNutrient) {
        return new  Protein(this.value+ baseNutrient.value);
    }

    @Override
    public Protein divide(Double coefficient) {
        return new Protein(this.value/coefficient);
    }
}

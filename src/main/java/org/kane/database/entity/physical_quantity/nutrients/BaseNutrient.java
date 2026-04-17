package org.kane.database.entity.physical_quantity.nutrients;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class BaseNutrient {
    public Double value;

    public void add(BaseNutrient baseNutrient){
        this.value = this.value+ baseNutrient.value;
    };

    public void divide(Double coefficient) {
        this.value = this.value / coefficient;
    }

    public void multiply(Double coefficient) {
        this.value = this.value * coefficient;
    }

    @JsonValue
    public Double toValue() {
        return this.value;
    }

}

package org.kane.database.entity.physical_quantity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

@Data
@AllArgsConstructor
public class ProductWeight {

    private Double value;

    public static ProductWeight calculateWeight(Double amount, Double coefficient){
        return new ProductWeight(amount/coefficient);
    }

    @JsonCreator
    public static Calories fromValue(Double value) {
        return new Calories(value);
    }

    @JsonValue
    public Double toValue() {
        return this.value;
    }

    public Double calculateAmount(Double coefficient){
        return this.value*coefficient;
    }

    public Double getWeightCoefficient(){
        return value/100.0;
    }

    public void add(ProductWeight productWeight){
        this.value+=productWeight.getValue();
    }

    public ProductWeight divide(Double coefficient){
        return new ProductWeight(this.value/coefficient);
    }

    public ProductWeight multiply(Double coefficient){
        return new ProductWeight(this.value*coefficient);
    }
}

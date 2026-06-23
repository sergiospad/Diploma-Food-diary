package org.kane.database.entity.physical_quantity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductWeight {

    private Double value;

    @JsonCreator
    public static ProductWeight fromValue(Double value) {
        return new ProductWeight(value);
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

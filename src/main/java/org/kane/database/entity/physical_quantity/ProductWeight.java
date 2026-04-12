package org.kane.database.entity.physical_quantity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class ProductWeight {

    private Double value;

    public static ProductWeight calculateWeight(Double amount, Double coefficient){
        return new ProductWeight(amount/coefficient);
    }

    public Double calculateAmount(Double coefficient){
        return this.value*coefficient;
    }

    public Double getWeightCoefficient(){
        return value/100.0;
    }

    public ProductWeight add(ProductWeight productWeight){
        return new ProductWeight(this.value+ productWeight.getValue());
    }

    public ProductWeight divide(Double coefficient){
        return new ProductWeight(this.value/coefficient);
    }
}

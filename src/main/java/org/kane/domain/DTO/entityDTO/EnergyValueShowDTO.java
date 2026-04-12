package org.kane.domain.DTO.entityDTO;

import lombok.Builder;
import lombok.Data;
import org.apache.lucene.search.Weight;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.enum_types.CaloricityType;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientEnergyDTO;

@Data
@Builder
public class EnergyValueShowDTO {
    private CaloricityType caloricityType;
    private ProductWeight productWeight;
    private Calories calories;
    private Protein protein;
    private Fat fat;
    private Carbs carbs;

    public EnergyValueShowDTO(){
        this.productWeight = new ProductWeight(0.0);
        this.calories = new Calories(0.0);
        this.protein = new Protein(0.0);
        this.fat = new Fat(0.0);
        this.carbs = new Carbs(0.0);
    }

    public EnergyValueShowDTO merge(IngredientEnergyDTO dto){
        return EnergyValueShowDTO.builder()
                .caloricityType(caloricityType)
                .productWeight(this.productWeight.add(dto.getProductWeight()))
                .calories(this.calories.add(dto.getCalories()))
                .protein(this.protein.add(dto.getProtein()))
                .fat(this.fat.add(dto.getFat()))
                .carbs(this.carbs.add(dto.getCarbs()))
                .build();
    }
    public EnergyValueShowDTO merge(EnergyValueShowDTO dto){
        return EnergyValueShowDTO.builder()
                .caloricityType(caloricityType)
                .productWeight(this.productWeight.add(dto.getProductWeight()))
                .calories(this.calories.add(dto.getCalories()))
                .protein(this.protein.add(dto.getProtein()))
                .fat(this.fat.add(dto.getFat()))
                .carbs(this.carbs.add(dto.getCarbs()))
                .build();
    }

    public EnergyValueShowDTO divide(Double coefficient){
        return EnergyValueShowDTO.builder()
                .productWeight(productWeight.divide(coefficient))
                .calories(calories.divide(coefficient))
                .protein(protein.divide(coefficient))
                .fat(fat.divide(coefficient))
                .carbs(carbs.divide(coefficient))
                .build();
    }
}

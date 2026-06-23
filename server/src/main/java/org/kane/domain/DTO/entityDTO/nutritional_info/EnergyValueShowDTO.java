package org.kane.domain.DTO.entityDTO.nutritional_info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.enum_types.CaloricityType;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientEnergyDTO;

@Data
@Builder
@AllArgsConstructor
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

    public void add(IngredientEnergyDTO ingredientEnergyDTO){
        this.productWeight.add(ingredientEnergyDTO.getProductWeight());
        this.calories.add(ingredientEnergyDTO.getCalories());
        this.protein.add(ingredientEnergyDTO.getProtein());
        this.fat.add(ingredientEnergyDTO.getFat());
        this.carbs.add(ingredientEnergyDTO.getCarbs());
    }

    public EnergyValueShowDTO divide(Double coefficient){
        var copy = copy();
        copy.calories.divide(coefficient);
        copy.protein.divide(coefficient);
        copy.fat.divide(coefficient);
        copy.carbs.divide(coefficient);
        return copy;
    }

    public EnergyValueShowDTO copy(){
        return EnergyValueShowDTO.builder()
                .caloricityType(caloricityType)
                .productWeight(new ProductWeight(productWeight.getValue()))
                .calories(new Calories(calories.toValue()))
                .protein(new Protein(protein.toValue()))
                .fat(new Fat(fat.toValue()))
                .carbs(new Carbs(carbs.toValue()))
                .build();
    }
}

package org.kane.domain.DTO.entityDTO.diary.meal;

import lombok.Data;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;

@Data
public abstract class NutritionalInfoDTO {
    private Calories calories;
    private Protein proteins;
    private Fat fat;
    private Carbs carbs;

    protected NutritionalInfoDTO() {
        setCalories(new Calories(0.0));
        setProteins(new Protein(0.0));
        setFat(new Fat(0.0));
        setCarbs(new Carbs(0.0));
    }

    public void add(NutritionalInfoDTO nutritionalInfoDTO) {
        this.getCalories().add(nutritionalInfoDTO.getCalories());
        this.getProteins().add(nutritionalInfoDTO.getProteins());
        this.getFat().add(nutritionalInfoDTO.getFat());
        this.getCarbs().add(nutritionalInfoDTO.getCarbs());
    }
}

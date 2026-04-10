package org.kane.domain.DTO.entityDTO.product;

import lombok.Data;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;

@Data
public class ProductEditDTO {

    Long id;
    String title;
    Long authorId;

    String description;
    Long categoryId;
    Calories calories;
    Protein protein;
    Fat fat;
    Carbs carbs;
    Boolean isPrivate;
}

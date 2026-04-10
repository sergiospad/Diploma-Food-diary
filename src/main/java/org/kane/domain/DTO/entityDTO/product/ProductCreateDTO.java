package org.kane.domain.DTO.entityDTO.product;

import lombok.Data;
import org.kane.auth.validations.existing_product.ExistingProduct;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;

@Data
public class ProductCreateDTO {

    @ExistingProduct
    String title;

    String description;
    Long categoryId;
    Calories calories;
    Protein protein;
    Fat fat;
    Carbs carbs;
    boolean isPrivate;
}

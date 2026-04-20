package org.kane.database.repository.product;

import org.kane.domain.DTO.entityDTO.recipe_recource.measure_unit.MeasureUnitDTO;
import org.kane.domain.DTO.entityDTO.nutritional_info.NutritionShowProjection;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;

import java.util.List;

public interface CustomProductRepository {
    List<ProductSearchDTO> findSearchDTO(String searchItem);
    List<MeasureUnitDTO> findMeasureUnitDTOByProductId(Long productId);
    String findNameById(Long id);

    NutritionShowProjection getNutritionsShowProjection(Long id);
    Boolean existsByName(String name);
}

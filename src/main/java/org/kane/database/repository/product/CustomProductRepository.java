package org.kane.database.repository.product;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.measure_unit.MeasureUnitDTO;
import org.kane.domain.DTO.entityDTO.nutritional_info.NutritionShowProjection;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;

import java.util.List;

public interface CustomProductRepository {
    List<ProductSearchDTO> findSearchDTO(String searchItem);
    List<MeasureUnitDTO> findMeasureUnitDTOByProductId(Long productId);
    String findNameById(Long id);

    List<ProductSearchDTO> getNutritionsSearch(String searchItem);

    NutritionShowProjection getNutritionsShowProjection(Long id);
}

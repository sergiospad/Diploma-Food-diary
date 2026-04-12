package org.kane.database.repository.product;

import org.kane.domain.DTO.entityDTO.measure_unit.MeasureUnitDTO;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;

import java.util.List;
import java.util.Optional;

public interface CustomProductRepository {
    List<ProductSearchDTO> findSearchDTO(String searchItem);
    List<MeasureUnitDTO> findMeasureUnitDTOByProductId(Long productId);
    String findNameById(Long id);

}

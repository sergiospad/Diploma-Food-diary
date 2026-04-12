package org.kane.domain.service.measure_unit;

import org.kane.domain.DTO.entityDTO.measure_unit.MeasureUnitDTO;

import java.util.List;

public interface MeasureUnitService {
    List<MeasureUnitDTO> findAllByIngredientID(Long productID);
}

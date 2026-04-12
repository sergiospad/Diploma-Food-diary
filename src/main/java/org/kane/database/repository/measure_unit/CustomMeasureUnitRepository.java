package org.kane.database.repository.measure_unit;

import org.kane.domain.DTO.entityDTO.measure_unit.MeasureUnitDTO;

import java.util.List;

public interface CustomMeasureUnitRepository {
    List<MeasureUnitDTO> findAllByIngredientID(Long ingredientID);
    MeasureUnitDTO findByIngredientID(Long ingredientID);
}

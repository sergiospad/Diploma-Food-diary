package org.kane.domain.service.recipe_recource.measure_unit;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.measure_unit.MeasureUnitDTO;

import java.util.List;

public interface MeasureUnitService {
    List<MeasureUnitDTO> findAllByIngredientID(Long productID);

    MeasureUnitDTO createMeasureUnit(String name);

    List<MeasureUnitDTO> getAllUnits();

    List<MeasureUnitDTO> getFreeUnitsByCategoryID(Long categoryID);
}

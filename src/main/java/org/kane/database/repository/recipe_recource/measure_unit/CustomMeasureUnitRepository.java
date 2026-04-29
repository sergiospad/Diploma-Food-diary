package org.kane.database.repository.recipe_recource.measure_unit;

import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;

import java.util.List;

public interface CustomMeasureUnitRepository {
    List<MeasureUnitDTO> findAllByIngredientID(Long ingredientID);
    MeasureUnitDTO findByIngredientID(Long ingredientID);
    List<MeasureUnitDTO> findAllDistinct();
    List<MeasureUnitDTO> findFreeMeasureUnits(Long categoryID);

}

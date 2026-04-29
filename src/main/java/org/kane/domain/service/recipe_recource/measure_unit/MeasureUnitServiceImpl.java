package org.kane.domain.service.recipe_recource.measure_unit;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.kane.database.repository.recipe_recource.measure_unit.MeasureUnitRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MeasureUnitServiceImpl implements MeasureUnitService {
    private final MeasureUnitRepository measureUnitRepository;
    @Override
    public List<MeasureUnitDTO> findAllByIngredientID(Long ingredientID) {
        MeasureUnitDTO specUnit = measureUnitRepository.findByIngredientID(ingredientID);
        List<MeasureUnitDTO> units = new ArrayList<>(measureUnitRepository.findAllByIngredientID(ingredientID));
        if (specUnit.getId() != null) {
            units.removeIf(u -> Objects.equals(u.getId(), specUnit.getId()));
            units.addFirst( specUnit);
        }
        return units;
    }

    @Override
    public MeasureUnitDTO createMeasureUnit(String name){
        var unit = MeasureUnit.builder().name(name).build();
        unit =  measureUnitRepository.save(unit);
        return MeasureUnitDTO.builder()
                .id(unit.getId())
                .name(unit.getName())
                .build();
    }

    @Override
    public List<MeasureUnitDTO> getAllUnits(){
        return measureUnitRepository.findAllDistinct();
    }

    @Override
    public List<MeasureUnitDTO> getFreeUnitsByCategoryID(Long categoryID){
        return measureUnitRepository.findFreeMeasureUnits(categoryID);
    }


}

package org.kane.domain.service.measure_unit;

import lombok.RequiredArgsConstructor;
import org.kane.database.repository.measure_unit.MeasureUnitRepository;
import org.kane.domain.DTO.entityDTO.measure_unit.MeasureUnitDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasureUnitServiceImpl implements MeasureUnitService {
    private final MeasureUnitRepository measureUnitRepository;
    @Override
    public List<MeasureUnitDTO> findAllByIngredientID(Long ingredientID) {
        var specUnit = measureUnitRepository.findByIngredientID(ingredientID);
        var units = measureUnitRepository.findAllByIngredientID(ingredientID);
        units = units.stream()
                .filter(u-> u.equals(specUnit))
                .toList();
        units.addFirst(specUnit);
        return units;
    }

}

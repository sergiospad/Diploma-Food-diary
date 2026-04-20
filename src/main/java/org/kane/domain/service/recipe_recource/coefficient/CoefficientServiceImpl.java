package org.kane.domain.service.recipe_recource.coefficient;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.recipe_recource.Category;
import org.kane.database.entity.recipe_recource.Coefficient;
import org.kane.database.repository.recipe_recource.category.CategoryRepository;
import org.kane.database.repository.recipe_recource.coefficient.CoefficientRepository;
import org.kane.database.repository.recipe_recource.measure_unit.MeasureUnitRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientEditDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientShowDTO;
import org.kane.domain.mappers.CoefficientMapperShow;
import org.kane.exceptions.not_found.CategoryNotFoundException;
import org.kane.exceptions.not_found.MeasureUnitNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoefficientServiceImpl implements CoefficientService{

    private final CoefficientRepository coefficientRepository;
    private final CategoryRepository categoryRepository;
    private final MeasureUnitRepository measureUnitRepository;
    private final CoefficientMapperShow coefficientMapperShow;

    @Transactional
    @Override
    public CoefficientShowDTO addCoefficient(CoefficientCreateDTO coefficientShowDTO, Category category){
        var coefficient = Coefficient.builder()
                .conversionFactor(coefficientShowDTO.getConversionFactor())
                .measureUnit(measureUnitRepository.findById(coefficientShowDTO
                        .getMeasureUnitId())
                        .orElseThrow(()-> new MeasureUnitNotFound("Measure Unit not found")))
                .category(category)
                .build();
        coefficient = coefficientRepository.save(coefficient);
        return coefficientMapperShow.map(coefficient);
    }

    @Transactional
    @Override
    public CoefficientShowDTO editCoefficient(CoefficientEditDTO coefficientEditDTO){
        var mu = measureUnitRepository.findById(coefficientEditDTO.getMeasureUnitID())
                .orElseThrow(()-> new MeasureUnitNotFound("Measure Unit not found"));
        var category = categoryRepository.findById(coefficientEditDTO.getCategoryID())
                .orElseThrow(()-> new CategoryNotFoundException("Category not found"));
        var coefficient = Coefficient.builder()
                .id(coefficientEditDTO.getId())
                .conversionFactor(coefficientEditDTO.getConversionFactor())
                .measureUnit(mu)
                .category(category)
                .build();
        coefficient = coefficientRepository.save(coefficient);
        return coefficientMapperShow.map(coefficient);
    }


}

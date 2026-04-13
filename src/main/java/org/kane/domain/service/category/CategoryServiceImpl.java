package org.kane.domain.service.category;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.recipe_recource.Category;
import org.kane.database.entity.recipe_recource.Coefficient;
import org.kane.database.repository.category.CategoryRepository;
import org.kane.database.repository.coefficient.CoefficientRepository;
import org.kane.database.repository.measure_unit.MeasureUnitRepository;
import org.kane.domain.DTO.entityDTO.category.CategoryCreateDTO;
import org.kane.domain.DTO.entityDTO.category.CategoryNameDTO;
import org.kane.domain.DTO.entityDTO.category.CategoryShowDTO;
import org.kane.domain.mappers.CategoryMapperShow;
import org.kane.exceptions.not_found.CategoryNotFoundException;
import org.kane.exceptions.not_found.MeasureUnitNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final MeasureUnitRepository measureUnitRepository;
    private final CoefficientRepository coefficientRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapperShow categoryMapperShow;

    @Override
    public List<CategoryNameDTO> getAll() {
        return categoryRepository.findAllCategories();
    }

    @Override
    @Transactional
    public CategoryShowDTO createCategory(CategoryCreateDTO categoryCreateDTO){
        var category = Category.builder().name(categoryCreateDTO.getName()).build();
        var coefficients = categoryCreateDTO.getCoefficients().stream().map(ccd-> Coefficient
                    .builder()
                    .conversionFactor(ccd.getConversionFactor())
                    .measureUnit(measureUnitRepository.findById(ccd.getMeasureUnitId())
                            .orElseThrow(()-> new MeasureUnitNotFound("Measure Unit Not Found")))
                    .build())
                .toList();
        category.setCoefficients(coefficients);
        category = categoryRepository.save(category);

        return getShowDTO(category.getId());
    }

    private CategoryShowDTO getShowDTO(Long categoryID) {
        var showDTO = categoryRepository.findCategoryById(categoryID)
                .map(categoryMapperShow::map)
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
        showDTO.setCoefficients(coefficientRepository.getShowDTOByCategoryID(categoryID));
        return showDTO;
    }

    @Override
    public List<CategoryShowDTO> getAllShowDTO() {
        return categoryRepository.findAllCategories().stream()
                .map(cat -> {
                            var showDTO = categoryMapperShow.map(cat);
                            showDTO.setCoefficients(coefficientRepository.getShowDTOByCategoryID(cat.getId()));
                            return showDTO;
                        }
                ).toList();
    }

    @Override
    public void editCategory(CategoryNameDTO categoryNameDTO){
        var category = categoryRepository.findById(categoryNameDTO.getId())
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
        category.setName(categoryNameDTO.getName());
        categoryRepository.save(category);
    }

}

package org.kane.domain.service.recipe_recource.category;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.recipe_recource.Category;
import org.kane.database.repository.recipe_recource.category.CategoryRepository;
import org.kane.database.repository.recipe_recource.coefficient.CoefficientRepository;
import org.kane.database.repository.recipe_recource.measure_unit.MeasureUnitRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryNameDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryShowDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CategoryAddCoefficientDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientCreateDTO;
import org.kane.domain.mappers.CategoryMapperShow;
import org.kane.domain.service.recipe_recource.coefficient.CoefficientService;
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
    private final CoefficientService coefficientService;

    @Override
    public List<CategoryNameDTO> getAll() {
        return categoryRepository.findAllCategories();
    }

    @Override
    @Transactional
    public CategoryShowDTO createCategory(CategoryCreateDTO categoryCreateDTO){
        var category = Category.builder().name(categoryCreateDTO.getName()).build();
        category = categoryRepository.save(category);
        var measureUnit = measureUnitRepository.findByName("г")
                .orElseThrow(()->new MeasureUnitNotFound("Measure Unit Not Found"));
        CoefficientCreateDTO startCoeff = CoefficientCreateDTO.builder()
                .measureUnitId(measureUnit.getId())
                .conversionFactor(1.0)
                .build();
        Category finalCategory = category;
        coefficientService.addCoefficient(startCoeff, finalCategory);
        categoryCreateDTO.getCoefficients()
                .forEach(cat-> coefficientService.addCoefficient(cat, finalCategory));

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

    @Transactional
    @Override
    public void editCategory(CategoryNameDTO categoryNameDTO){
        var category = categoryRepository.findById(categoryNameDTO.getId())
                .orElseThrow(()-> new CategoryNotFoundException("Category Not Found"));
        category.setName(categoryNameDTO.getName());
        categoryRepository.save(category);
    }

    @Transactional
    @Override
    public CategoryShowDTO addCoefficient(CategoryAddCoefficientDTO coefficientDTO) {
        var category = categoryRepository.findById(coefficientDTO.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category Not Found"));
        coefficientDTO.getCoefficients()
                .forEach(dto -> coefficientService.addCoefficient(dto, category));
        return getShowDTO(category.getId());
    }

}

package org.kane.domain.service.recipe_recource.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.recipe_recource.Category;
import org.kane.database.entity.recipe_recource.Coefficient;
import org.kane.database.repository.recipe_recource.category.CategoryRepository;
import org.kane.database.repository.recipe_recource.measure_unit.MeasureUnitRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryNameDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CategoryAddCoefficientDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.coefficient.CoefficientShowDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-category-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CategoryServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MeasureUnitRepository measureUnitRepository;

    private List<CoefficientCreateDTO> savedCoeff;

    @BeforeEach
    void setUp() {
        savedCoeff = new ArrayList<>();
        var coeff = CoefficientCreateDTO.builder()
                .measureUnitId(3L)
                .conversionFactor(200.0)
                .build();
        savedCoeff.add(coeff);
        coeff = CoefficientCreateDTO.builder()
                .measureUnitId(4L)
                .conversionFactor(1000.0)
                .build();
        savedCoeff.add(coeff);
    }

    @Test
    void getAll() {
        var categories = categoryService.getAll();
        assertThat(categories).isNotNull().hasSize(6);
        var ids = LongStream.range(1, categories.size()).boxed().toList();
        var names = ids.stream().map(categoryRepository::findById)
                .map(Optional::orElseThrow)
                .map(Category::getName)
                .toList();
        assertThat(categories.stream().map(CategoryNameDTO::getId).toList()).containsAll(ids);
        assertThat(categories.stream().map(CategoryNameDTO::getName).toList()).containsAll(names);
    }

    @Test
    void createCategory() {
        CategoryCreateDTO categoryCreateDTO = CategoryCreateDTO.builder()
                .name("TestCategory")
                .coefficients(savedCoeff)
                .build();
        var result = categoryService.createCategory(categoryCreateDTO);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(7L);
        assertThat(result.getName()).isEqualTo(categoryCreateDTO.getName());
        assertThat(result.getCoefficients()).isNotEmpty().hasSize(3);
        var list = result.getCoefficients();
        assertThat(list.stream().map(CoefficientShowDTO::getMeasureUnitName).toList())
                .containsExactlyInAnyOrder("г", "мл", "л");
        assertThat(list.stream().map(CoefficientShowDTO::getId).toList())
                .containsExactlyInAnyOrder(7L, 8L, 9L);
        assertThat(list.stream().map(CoefficientShowDTO::getConversionFactor).toList())
                .containsExactlyInAnyOrder(1.0, savedCoeff.get(0).getConversionFactor(), savedCoeff.get(1).getConversionFactor());
    }

    @Test
    void getAllShowDTO() {
        var categories = categoryService.getAllShowDTO();
        assertThat(categories).isNotNull().hasSize(6);
        assertThat(categories.getFirst().getCoefficients()).isNotEmpty().hasSize(2);
    }

    @Test
    void editCategory() {
        var cndto = new CategoryNameDTO(1L, "TestCategory");
        var cat = categoryRepository.findById(cndto.getId()).orElseThrow();
        assertThat(cat.getName()).isEqualTo("Мясо");
        categoryService.editCategory(cndto);
        cat = categoryRepository.findById(cndto.getId()).orElseThrow();
        assertThat(cat.getName()).isEqualTo(cndto.getName());

    }

    @Test
    void addCoefficient(){
        var cacdto = CategoryAddCoefficientDTO.builder()
                .id(1L)
                .coefficients(savedCoeff)
                .build();
        var before = categoryService.getAllShowDTO().stream()
                .filter(c -> c.getId().equals(cacdto.getId()))
                .findFirst()
                .orElseThrow();
        assertThat(before.getCoefficients()).isNotEmpty().hasSize(2);
        var result = categoryService.addCoefficient(cacdto);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(cacdto.getId());
        assertThat(result.getCoefficients()).isNotEmpty().hasSize(4);
    }
}
package org.kane.database.repository.recipe_recource.coefficient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Product;
import org.kane.database.entity.recipe_recource.Category;
import org.kane.database.entity.recipe_recource.Coefficient;
import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-coefficient-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CustomCoefficientRepositoryTest extends IntegrationTestBase {
    @Autowired
    private CoefficientRepository coefficientRepository;

    @Autowired
    private SavedEntities savedEntities;

    private Product savedProduct;
    private Category savedCategory;
    private Coefficient savedCoefficient2;
    private Coefficient savedCoefficient;
    private MeasureUnit savedMeasureUnit;
    private MeasureUnit savedMeasureUnit2;

    @BeforeEach
    void setUp() {
        savedProduct = savedEntities.getProduct();
        savedCategory = savedEntities.getCategory();
        savedCoefficient = savedEntities.getCoefficient();
        savedMeasureUnit = savedEntities.getMeasureUnit();
        savedCoefficient.setMeasureUnit(savedMeasureUnit);
        savedMeasureUnit2 = MeasureUnit.builder().id(2L).name("кг").build();
        savedCoefficient2 = Coefficient
                .builder()
                .id(6L)
                .conversionFactor(200.0000)
                .measureUnit(savedMeasureUnit2)
                .build();
        savedCategory.setCoefficients(List.of(savedCoefficient, savedCoefficient2));
        savedProduct.setCategory(savedCategory);
    }

    @Test
    void getCoefficientByProductID() {
        var coeff = coefficientRepository.getCoefficientByProductID(savedProduct.getId(), savedMeasureUnit.getId());
        assertThat(coeff).isNotNull().isEqualTo(savedCoefficient.getConversionFactor());
        coeff = coefficientRepository.getCoefficientByProductID(savedProduct.getId(), savedMeasureUnit2.getId());
        assertThat(coeff).isNotNull().isEqualTo(savedCoefficient2.getConversionFactor());
    }

    @Test
    void getShowDTOByCategoryID() {
        var coeffs =  coefficientRepository.getShowDTOByCategoryID(savedCategory.getId());
        assertThat(coeffs).isNotNull();
        var measureUnits = List.of(savedMeasureUnit.getName(), savedMeasureUnit2.getName());

        System.out.println(coeffs);
        coeffs.forEach(coeff ->
        {
            assertThat(coeff.getMeasureUnitName()).as("Проверка обозначений ед. изм").isIn(measureUnits);
        });
        var factors = List.of(savedCoefficient.getConversionFactor(), savedCoefficient2.getConversionFactor());
        var coeffIds = List.of(savedCoefficient.getId(), savedCoefficient2.getId());
        coeffs.forEach(coeff ->{
                assertThat(coeff.getConversionFactor()).isIn(factors);
            assertThat(coeff.getId()).isIn(coeffIds);
        });
    }
}
package org.kane.domain.service.recipe_recource.coefficient;

import org.junit.jupiter.api.Test;
import org.kane.database.repository.recipe_recource.category.CategoryRepository;
import org.kane.database.repository.recipe_recource.coefficient.CoefficientRepository;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.coefficient.CoefficientCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.coefficient.CoefficientEditDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
class CoefficientServiceTest extends IntegrationTestServiceBase {


    @Autowired
    private CoefficientService coefficientService;

    @Test
    void addCoefficient() {
        var ccdto = CoefficientCreateDTO.builder()
                .categoryId(6L)
                .measureUnitId(4L)
                .conversionFactor(300.0).build();
        var csdto = coefficientService.addCoefficient(ccdto);
        assertThat(csdto).isNotNull();
        assertThat(csdto.getId()).isEqualTo(7L);
        assertThat(csdto.getConversionFactor()).isEqualTo(300.0);
        assertThat(csdto.getMeasureUnitName()).isEqualTo("л");
    }

    @Test
    void editCoefficient() {
        var cedto = CoefficientEditDTO.builder()
                .id(1L)
                .categoryID(4L)
                .conversionFactor(1000.0)
                .measureUnitID(4L)
                .build();
        var csdto = coefficientService.editCoefficient(cedto);
        assertThat(csdto).isNotNull();
        assertThat(csdto.getId()).isEqualTo(cedto.getId());
        assertThat(csdto.getConversionFactor()).isEqualTo(cedto.getConversionFactor());
        assertThat(csdto.getMeasureUnitName()).isEqualTo("л");
    }
}
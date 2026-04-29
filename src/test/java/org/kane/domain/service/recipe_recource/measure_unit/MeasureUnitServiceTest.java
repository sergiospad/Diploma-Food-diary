package org.kane.domain.service.recipe_recource.measure_unit;

import org.junit.jupiter.api.Test;
import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-measure-unit-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class MeasureUnitServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private MeasureUnitService measureUnitService;

    @Test
    void findAllByIngredientID() {
        var list = measureUnitService.findAllByIngredientID(1L);
        assertThat(list).isNotEmpty().hasSize(2);
        assertThat(list.stream().map(MeasureUnitDTO::getId).toList()).containsExactly(1L, 2L);
        assertThat(list.stream().map(MeasureUnitDTO::getName).toList()).containsExactly("г", "кг");
    }

    @Test
    void createMeasureUnit() {
        var mu = measureUnitService.createMeasureUnit("testUnit");
        assertThat(mu).isNotNull();
        assertThat(mu.getId()).isEqualTo(8L);
        assertThat(mu.getName()).isEqualTo("testUnit");
    }

    @Test
    void getAllUnits() {
        var list = measureUnitService.getAllUnits();
        assertThat(list).isNotEmpty().hasSize(7);
    }

    @Test
    void getFreeUnitsByCategoryID() {
        var units = measureUnitService.getFreeUnitsByCategoryID(1L);
        assertThat(units).isNotEmpty().hasSize(5);
        assertThat(units.stream().map(MeasureUnitDTO::getId).toList())
                .containsExactlyInAnyOrder( 3L, 4L, 5L, 6L, 7L)
                .doesNotContain(1L, 2L);
    }
}
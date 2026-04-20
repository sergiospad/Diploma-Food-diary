package org.kane.database.repository.recipe_recource.measure_unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.kane.domain.DTO.entityDTO.recipe_recource.measure_unit.MeasureUnitDTO;
import org.kane.exceptions.not_found.MeasureUnitNotFound;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
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
class MeasureUnitRepositoryImplTest extends IntegrationTestBase {

    @Autowired
    private MeasureUnitRepository measureUnitRepository;

    private MeasureUnit savedMeasureUnit;
    private Ingredient savedIngredient;

    @Autowired
    private SavedEntities savedEntities;

    @BeforeEach
    void setUp() {
        savedIngredient = savedEntities.getIngredient();
        savedMeasureUnit = savedEntities.getMeasureUnit();
        savedIngredient.setSpecMeasureUnit(savedMeasureUnit);
    }

    @Test
    void findById(){
        var mu = measureUnitRepository.findById(savedMeasureUnit.getId())
                .orElseThrow(()-> new MeasureUnitNotFound("MeasureUnit not found"));
        assertThat(mu).isNotNull();
        assertThat(mu.getId()).isEqualTo(savedMeasureUnit.getId());
        assertThat(mu.getName()).isEqualTo(savedMeasureUnit.getName());
    }

    @Test
    void findAllByIngredientID() {
        var mu =  measureUnitRepository.findAllByIngredientID(1L);
        assertThat(mu).isNotNull().hasSize(2);
        var muId = mu.stream().map(MeasureUnitDTO::getId).toList();
        assertThat(muId).containsExactlyInAnyOrder(1L, 2L);
        var muNames = mu.stream().map(MeasureUnitDTO::getName).toList();
        assertThat(muNames).containsExactlyInAnyOrder("г", "кг");
    }

    @Test
    void findByIngredientID() {
        var mu = measureUnitRepository.findByIngredientID(savedIngredient.getId());
        assertThat(mu).isNotNull();
        assertThat(mu.getId()).isEqualTo(savedIngredient.getId());
        assertThat(mu.getName()).isEqualTo(savedMeasureUnit.getName());
    }

    @Test
    void findAllDistinct() {
        var mu = measureUnitRepository.findAllDistinct();
        assertThat(mu).isNotNull().hasSize(7);
    }

    @Test
    void findFreeMeasureUnits() {
        var list = measureUnitRepository.findFreeMeasureUnits(1L);
        assertThat(list).isNotNull().hasSize(5);
        var listID = list.stream().map(MeasureUnitDTO::getId).toList();
        assertThat(listID).contains(5L, 3L, 4L).doesNotContain(1L, 2L);

    }
}
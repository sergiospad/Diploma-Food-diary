package org.kane.domain.service.recipe_recource.measure_unit;

import org.junit.jupiter.api.Test;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void findAllByIngredientID() {
    }

    @Test
    void createMeasureUnit() {
    }

    @Test
    void getAllUnits() {
    }

    @Test
    void getFreeUnitsByCategoryID() {
    }
}
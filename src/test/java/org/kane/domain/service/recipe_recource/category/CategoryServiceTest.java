package org.kane.domain.service.recipe_recource.category;

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
                scripts = "classpath:sql/recipe_resource/data-category-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CategoryServiceTest extends IntegrationTestServiceBase {

    @Test
    void getAll() {

    }

    @Test
    void createCategory() {
    }

    @Test
    void getAllShowDTO() {
    }

    @Test
    void editCategory() {
    }
}
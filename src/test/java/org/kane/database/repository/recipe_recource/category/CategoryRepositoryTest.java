package org.kane.database.repository.recipe_recource.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.recipe_recource.Category;

import org.kane.exceptions.not_found.CategoryNotFoundException;
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
                scripts = "classpath:sql/recipe_resource/data-category-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CategoryRepositoryTest extends IntegrationTestBase {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SavedEntities savedEntities;

    private Category savedCategory;

    @BeforeEach
    void setUp() {
        savedCategory = savedEntities.getCategory();
    }

    @Test
    void findById() {
        var category = categoryRepository.findById(1L)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(savedCategory.getId());
        assertThat(category.getName()).isEqualTo(savedCategory.getName());
    }

    @Test
    void findCategoryById() {
        var category = categoryRepository.findCategoryById(1L)
                .orElseThrow(()->new CategoryNotFoundException("Category not found"));

        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(savedCategory.getId());
        assertThat(category.getName()).isEqualTo(savedCategory.getName());
    }

    @Test
    void findAllCategories(){
        var categories = categoryRepository.findAllCategories();
        assertThat(categories).isNotNull();
        assertThat(categories.size()).isEqualTo(6);
    }
}
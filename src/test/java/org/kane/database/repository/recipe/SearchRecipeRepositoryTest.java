package org.kane.database.repository.recipe;

import jakarta.persistence.EntityManager;
import org.hibernate.search.mapper.orm.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.User;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.domain.DTO.entityDTO.recipe.RecipeSummarySearchDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeTitleSearchDTO;
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
                scripts = "classpath:sql/recipe/data-recipe-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class SearchRecipeRepositoryTest extends IntegrationTestBase {
    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private EntityManager em;

    private Recipe savedRecipe;
    private User savedUser;
    private ImageModel savedIllustrationImage;
    private ImageModel userAvatar;

    @BeforeEach
    void beforeEach() {
        savedRecipe = savedEntities.getRecipe();
        savedUser = savedEntities.getUser();
        savedRecipe.setAuthor(savedUser);
        savedIllustrationImage = savedEntities.getIllustration();
        savedRecipe.setIllustration(savedIllustrationImage);
        userAvatar = savedEntities.getAvatar();
        savedUser.setAvatar(userAvatar);
        try {
            Search.session(em)
                    .massIndexer()
                    .startAndWait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to initialize Hibernate Search index for tests", e);
        }
    }

    @Test
    void findSummaryDTOByItem() {
        var recipes = recipeRepository.findSummaryDTOByItem("салат");
        assertThat(recipes).isNotEmpty();
        assertThat(recipes.size()).isEqualTo(2);
        var expResultsId = List.of(5L, 7L);
        recipes.stream()
                .map(RecipeSummarySearchDTO::getId)
                .forEach(r -> assertThat(r).isIn(expResultsId));
        var expResultsNames = expResultsId.stream()
                .map(r -> recipeRepository.findById(r).orElseThrow())
                .map(Recipe::getName)
                .toList();
        recipes.stream()
                .map(RecipeSummarySearchDTO::getName)
                .forEach(r -> assertThat(r).isIn(expResultsNames));
        var expResultsSummaries = expResultsId.stream().map(r -> recipeRepository.findById(r).orElseThrow())
                .map(Recipe::getSummary)
                .toList();
        recipes.stream()
                .map(RecipeSummarySearchDTO::getSummary)
                .forEach(r -> assertThat(r).isIn(expResultsSummaries));
    }

    @Test
    void findTitleDTOByItem() {
        var recipes = recipeRepository.findTitleDTOByItem("салат");
        assertThat(recipes).isNotEmpty();
        assertThat(recipes.size()).isEqualTo(2);
        var expResultsId = List.of(5L, 7L);
        recipes.stream()
                .map(RecipeTitleSearchDTO::getId)
                .forEach(r -> assertThat(r).isIn(expResultsId));
        var expResultsNames = expResultsId.stream()
                .map(r -> recipeRepository.findById(r).orElseThrow())
                .map(Recipe::getName)
                .toList();
        recipes.stream()
                .map(RecipeTitleSearchDTO::getName)
                .forEach(r -> assertThat(r).isIn(expResultsNames));
    }

    @Test
    void findTitleDTOByItem2() {
        var recipes = recipeRepository.findTitleDTOByItem("сgлаr");
        assertThat(recipes).isNotEmpty();
        assertThat(recipes.size()).isEqualTo(2);
        var expResultsId = List.of(5L, 7L);
        recipes.stream()
                .map(RecipeTitleSearchDTO::getId)
                .forEach(r -> assertThat(r).isIn(expResultsId));
        var expResultsNames = expResultsId.stream()
                .map(r -> recipeRepository.findById(r).orElseThrow())
                .map(Recipe::getName)
                .toList();
        recipes.stream()
                .map(RecipeTitleSearchDTO::getName)
                .forEach(r -> assertThat(r).isIn(expResultsNames));
    }

    @Test
    void findTitleDTOByItem3() {
        var recipes = recipeRepository.findTitleDTOByItem("сgaаr");
        assertThat(recipes).isEmpty();
    }
}

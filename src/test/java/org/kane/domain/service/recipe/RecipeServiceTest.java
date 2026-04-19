package org.kane.domain.service.recipe;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.ingredient.IngredientEditDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeEditDTO;
import org.kane.integration.IntegrationTestServiceBase;
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
                scripts = "classpath:sql/data-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class RecipeServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private RecipeService recipeService;

    @Test
    void searchBySummary() {
    }

    @Test
    void searchByTitle() {
    }

    @Test
    void updateRecipe() {
        var recipeEditDTO = RecipeEditDTO.builder()
                .id(4L)
                .name("Не Куриный Суп")
                .summary("Не Наваристый Не куриный Не суп Не с лапшой и не овощами")
                .cookingTime((short)90)
                .addTags(List.of(3L, 4L))
                .removeTags(List.of(1L))
                .isPrivate(true)
                .build();
        var editedStage = new CookingStageEditDescDTO(1L, "Не Нарезайте курицу и овощи");
        recipeEditDTO.setEditedStages(List.of(editedStage));
        var editedIngredients = IngredientEditDTO.builder()
                .id(1L)
                .productID(10L)
                .amount(200.0)
                .measureUnitID(2L)
                .build();
        recipeEditDTO.setEditedIngredients(List.of(editedIngredients));
        var editedRecipe = recipeService.updateRecipe(recipeEditDTO);
        assertThat(editedRecipe).isNotNull();
        assertThat(editedRecipe.getId()).isEqualTo(recipeEditDTO.getId());
    }

    @Test
    void showRecipe() {
    }
}

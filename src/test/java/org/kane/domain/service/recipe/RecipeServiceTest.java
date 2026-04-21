package org.kane.domain.service.recipe;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.enum_types.CaloricityType;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.domain.DTO.entityDTO.recipe.*;
import org.kane.domain.DTO.entityDTO.recipe_recource.TagDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageEditDescDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientEditDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private SavedEntities savedEntities;

    @Test
    void searchBySummary() {
        var results = recipeService.searchBySummary("салат");
        assertThat(results).hasSize(2);
        assertThat(results.stream().map(RecipeSummarySearchDTO::getId).toList()).containsExactlyInAnyOrder(5L, 7L);
    }

    @Test
    void searchByTitle() {
        var results = recipeService.searchByTitle("cалат");
        assertThat(results).hasSize(2);
        assertThat(results.stream().map(RecipeTitleSearchDTO::getName).toList()).containsExactlyInAnyOrder("Салат Цезарь", "Греческий салат");
    }

    @Test
    void createRecipe(){
        Principal principal = ()->"chef_mike";
        var rcdto = RecipeCreateDTO.builder()
                .name("testName")
                .summary("testSummary")
                .illustrationID(1L)
                .isPrivate(true)
                .cookingTime((short)60)
                .build();
        var icdto = IngredientCreateDTO.builder()
                .productID(8L)
                .amount(200.0)
                .measureUnitID(1L)
                .build();
        var stage = CookingStageCreateDTO.builder()
                .description("testDescription")
                .stageNumber((short)1)
                .imageID(1L)
                .build();
        var stage2 = CookingStageCreateDTO.builder()
                .description("testDescription2")
                .stageNumber((short)2)
                .build();
        rcdto.setTags(List.of(6L, 7L, 8L));
        rcdto.setIngredients(List.of(icdto));
        rcdto.setStages(List.of(stage, stage2));
        var recipe = recipeService.createRecipe(principal, rcdto);
        assertThat(recipe.getId()).isNotNull().isEqualTo(11L);

    }

    @Test
    void updateRecipe() {
        var recipe = recipeRepository.findById(4L).orElseThrow();
        var recipeEditDTO = RecipeEditDTO.builder()
                .id(recipe.getId())
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
                .measureUnitID(5L)
                .build();
        recipeEditDTO.setEditedIngredients(List.of(editedIngredients));
        var editedRecipe = recipeService.updateRecipe(recipeEditDTO);
        assertThat(editedRecipe).isNotNull();
        assertThat(editedRecipe.getId()).isEqualTo(recipeEditDTO.getId());
        assertThat(editedRecipe.getName()).isEqualTo(recipeEditDTO.getName());
        assertThat(editedRecipe.getSummary()).isEqualTo(recipeEditDTO.getSummary());
        assertThat(editedRecipe.getCookingTime()).isEqualTo(recipeEditDTO.getCookingTime());
        assertThat(editedRecipe.getTags().stream().map(TagDTO::getId).toList()).containsExactlyInAnyOrder(3L, 4L, 5L);
        var ingredient = editedRecipe.getIngredients().stream().filter(i -> Objects.equals(i.getId(), editedIngredients.getId())).findFirst().orElseThrow();
        assertThat(ingredient.getId()).isEqualTo(editedIngredients.getId());
        assertThat(ingredient.getAmount()).isEqualTo(200.0);
        assertThat(ingredient.getUnits().getFirst().getId()).isEqualTo(5L);
        var stage = editedRecipe.getCookingStages().stream().filter(i -> Objects.equals(i.getId(), editedStage.getId())).findFirst().orElseThrow();
        assertThat(stage.getId()).isEqualTo(editedStage.getId());
        assertThat(stage.getDescription()).isEqualTo(editedStage.getDescription());
    }

    @Test
    void showRecipe() {
        var savedRecipe = savedEntities.getRecipe();
        var savedAuthor = savedEntities.getUser();
        var savedImage = savedEntities.getAvatar();
        savedAuthor.setAvatar(savedImage);
        savedRecipe.setAuthor(savedAuthor);

        var recipe = recipeService.showRecipe(savedRecipe.getId());
        assertThat(recipe.getId()).isEqualTo(savedRecipe.getId());
        assertThat(recipe.getName()).isEqualTo(savedRecipe.getName());
        assertThat(recipe.getSummary()).isEqualTo(savedRecipe.getSummary());
        assertThat(recipe.getCookingTime()).isEqualTo(savedRecipe.getCookingTime());
        assertThat(recipe.getAuthorName()).isEqualTo(savedRecipe.getAuthor().getUsername());
        assertThat(recipe.getAvatarID()).isEqualTo(savedAuthor.getAvatar().getId());
        assertThat(recipe.getTags().stream().map(TagDTO::getId).toList()).containsExactlyInAnyOrder(1L, 5L);
        assertThat(recipe.getTags().stream().map(TagDTO::getName).toList()).containsExactly("Супы", "Диетическое");
        assertThat(recipe.getIllustrationID()).isEqualTo(3L);
        assertThat(recipe.getEnergy().getCaloricityType()).isEqualTo(CaloricityType.PER_HUNDRED);
        assertThat(recipe.getEnergy().getCalories()).isEqualTo(new Calories(123.5));
        assertThat(Math.abs(recipe.getEnergy().getProtein().getValue())-16.9).isLessThan(0.01);
        assertThat(Math.abs(recipe.getEnergy().getFat().getValue() - 1.975)).isLessThan(0.01);
        assertThat(Math.abs(recipe.getEnergy().getCarbs().getValue()-8.8)).isLessThan(0.06);
    }
}

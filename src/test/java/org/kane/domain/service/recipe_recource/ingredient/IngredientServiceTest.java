package org.kane.domain.service.recipe_recource.ingredient;

import org.junit.jupiter.api.Test;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.recipe_recource.ingredient.IngredientRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientChangeDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientEditDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientShowDTO;
import org.kane.domain.service.recipe.RecipeService;
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
                scripts = "classpath:sql/recipe_resource/data-measure-unit-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class IngredientServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeService recipeService;

    @Test
    void createIngredient() {
        var recipe = recipeRepository.findById(4L).orElseThrow();
        var ingredientCreateDTO = IngredientCreateDTO.builder()
                .productID(1L)
                .amount(200.0)
                .measureUnitID(1L)
                .build();
        var ingr = ingredientService.createIngredient(ingredientCreateDTO, recipe);
        assertThat(ingr).isNotNull();
        assertThat(ingr.getId()).isEqualTo(9L);
        assertThat(ingr.getRecipe().getId()).isEqualTo(recipe.getId());
        assertThat(ingr.getWeight().getValue()).isEqualTo(200.0);
        assertThat(ingr.getSpecMeasureUnit().getId()).isEqualTo(1L);

    }

    @Test
    void updateIngredient() {
        var ingrEdit = IngredientEditDTO.builder()
                .id(1L)
                .productID(3L)
                .build();
        var ingr = ingredientService.updateIngredient(ingrEdit);
        assertThat(ingr).isNotNull();
        assertThat(ingr.getId()).isEqualTo(1L);
        assertThat(ingr.getProduct().getId()).isEqualTo(3L);
        assertThat(ingr.getSpecMeasureUnit().getId()).isEqualTo(1L);
    }
    @Test
    void updateIngredient2() {
        var ingrEdit = IngredientEditDTO.builder()
                .id(1L)
                .measureUnitID(3L)
                .build();
        var ingr = ingredientService.updateIngredient(ingrEdit);
        assertThat(ingr).isNotNull();
        assertThat(ingr.getId()).isEqualTo(1L);
        assertThat(ingr.getProduct().getId()).isEqualTo(1L);
        assertThat(ingr.getSpecMeasureUnit().getId()).isEqualTo(3L);
    }

    @Test
    void updateIngredient3() {
        var ingrEdit = IngredientEditDTO.builder()
                .id(1L)
                .measureUnitID(2L)
                .amount(400.0)
                .build();
        var ingr = ingredientService.updateIngredient(ingrEdit);
        assertThat(ingr).isNotNull();
        assertThat(ingr.getId()).isEqualTo(ingrEdit.getId());
        assertThat(ingr.getProduct().getId()).isEqualTo(1L);
        assertThat(ingr.getSpecMeasureUnit().getId()).as("Measure Unit").isEqualTo(ingrEdit.getMeasureUnitID());
        assertThat(ingr.getWeight().getValue()).isEqualTo(2.0);
    }
    @Test
    void updateIngredien4() {
        var ingrEdit = IngredientEditDTO.builder()
                .id(1L)
                .measureUnitID(2L)
                .amount(400.0)
                .build();
        var ingr = ingredientService.updateIngredient(ingrEdit);
        assertThat(ingr).isNotNull();
        assertThat(ingr.getId()).isEqualTo(1L);
        assertThat(ingr.getProduct().getId()).isEqualTo(1L);
        assertThat(ingr.getSpecMeasureUnit().getId()).isEqualTo(2L);
        assertThat(ingr.getWeight().getValue()).isEqualTo(2.0);
    }


    @Test
    void removeIngredient() {
        var ing = ingredientRepository.findById(1L).orElseThrow();
        var recId = ing.getRecipe().getId();
        assertThat(ingredientService.getShowIngredients(recId)).hasSize(3);
        ingredientService.removeIngredient(1L);
        var ingUpd = ingredientService.getShowIngredients(recId);
        assertThat(ingUpd).hasSize(2);
        assertThat(ingUpd.stream().map(IngredientShowDTO::getId).toList())
                .doesNotContain(1L)
                .containsAll(List.of(2L, 3L));
    }
    @Test
    void getShowIngredients() {
        var list = ingredientService.getShowIngredients(4L);
        assertThat(list).hasSize(3);
        assertThat(list.stream().map(IngredientShowDTO::getId).toList()).hasSize(3).containsAll(List.of(1L, 2L, 3L));
        assertThat(list.stream().map(IngredientShowDTO::getProductName).toList())
                .containsExactly("Куриная грудка", "Рис белый","Овсяная каша");
        assertThat(list.stream().map(IngredientShowDTO::getAmount).toList()).containsExactly(200.0, 10000.0, 10000.0);
        assertThat(list.get(0).getUnits()).hasSize(2);
        assertThat(list.get(1).getUnits()).hasSize(2);

    }

    @Test
    void toggleMeasureUnit() {
        var ch = IngredientChangeDTO.builder()
                .ingredientID(1L)
                .measureID(2L)
                .build();
        var ing =  ingredientService.toggleMeasureUnit(ch);
        assertThat(ing).isNotNull();
        assertThat(ing.getId()).isEqualTo(1L);
        assertThat(ing.getAmount()).isEqualTo(40000.0);
    }
}
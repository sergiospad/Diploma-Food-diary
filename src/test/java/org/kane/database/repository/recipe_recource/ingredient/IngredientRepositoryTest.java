package org.kane.database.repository.recipe_recource.ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Product;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientEnergyDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.ingredient.IngredientPreShowProjection;
import org.kane.exceptions.not_found.IngredientNotFoundException;
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
class IngredientRepositoryTest extends IntegrationTestBase {

    private Ingredient savedIngredient;
    private Product savedProduct;
    private MeasureUnit savedMeasureUnit;
    private Recipe savedRecipe;
    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        savedIngredient = savedEntities.getIngredient();
        savedProduct = savedEntities.getProduct();
        savedMeasureUnit = savedEntities.getMeasureUnit();
        savedRecipe = savedEntities.getRecipe();
        savedIngredient.setRecipe(savedRecipe);
        savedIngredient.setProduct(savedProduct);
        savedIngredient.setSpecMeasureUnit(savedMeasureUnit);
    }

    @Test
    void findById() {
        var ing = ingredientRepository.findById(savedIngredient.getId())
                .orElseThrow(()->new IngredientNotFoundException("Ingredient not found"));
        assertThat(ing).isNotNull();
        assertThat(ing.getId()).isEqualTo(savedIngredient.getId());
        assertThat(ing.getRecipe().getId()).isEqualTo(savedRecipe.getId());
        assertThat(ing.getProduct().getId()).isEqualTo(savedProduct.getId());
        assertThat(ing.getSpecMeasureUnit().getId()).isEqualTo(savedMeasureUnit.getId());
        assertThat(ing.getWeight()).isEqualTo(savedIngredient.getWeight());
    }
    @Test
    void findPreShowDTO() {
        var ing = ingredientRepository.findPreShowDTO(savedRecipe.getId());
        assertThat(ing).isNotNull().hasSize(3);
        var ingID = ing.stream().map(IngredientPreShowProjection::getId).toList();
        assertThat(ingID).contains(1L, 2L, 3L);
        var prodID = ing.stream().map(IngredientPreShowProjection::getProductID).toList();
        assertThat(prodID).contains(1L, 2L, 6L);
        var prodWeights = ing.stream().map(IngredientPreShowProjection::getAmount).toList();
        assertThat(prodWeights).contains(new ProductWeight(200.0), new ProductWeight(100.0));
        var measureUnitID = ing.stream().map(IngredientPreShowProjection::getMeasureUnitID).toList();
        assertThat(measureUnitID).contains(1L);
    }

    @Test
    void findPreShowDTOById() {
        var ing = ingredientRepository.findPreShowDTOById(savedIngredient.getId());
        assertThat(ing).isNotNull();
        assertThat(ing.getId()).isEqualTo(savedIngredient.getId());
        assertThat(ing.getProductID()).isEqualTo(savedProduct.getId());
        assertThat(ing.getAmount()).isEqualTo(savedIngredient.getWeight());
        assertThat(ing.getMeasureUnitID()).isEqualTo(savedMeasureUnit.getId());
    }

    @Test
    void findIngredientEnergyDTOByRecipeID() {
        var ing = ingredientRepository.findIngredientEnergyDTOByRecipeID(savedRecipe.getId());
        assertThat(ing).isNotNull().hasSize(3);
        var ingID = ing.stream().map(IngredientEnergyDTO::getId).toList();
        assertThat(ingID).contains(1L, 2L, 3L);
        var weights = ing.stream().map(IngredientEnergyDTO::getProductWeight).toList();
        assertThat(weights).contains(new ProductWeight(200.0), new ProductWeight(100.0));
        var calories = ing.stream().map(IngredientEnergyDTO::getCalories).toList();
        assertThat(calories).contains(new Calories(330.0),  new Calories(130.0),  new Calories(68.0));
        var proteins = ing.stream().map(IngredientEnergyDTO::getProtein).toList();
        assertThat(proteins).contains(new Protein(62.00),  new Protein(2.70),  new Protein(2.50));
        var fat = ing.stream().map(IngredientEnergyDTO::getFat).toList();
        assertThat(fat).contains(new Fat(7.20),  new Fat(0.30),  new Fat(1.50));
        var carbs = ing.stream().map(IngredientEnergyDTO::getCarbs).toList();
        assertThat(carbs).contains(new Carbs(0.00),  new Carbs(28.00),  new Carbs(12.00));
    }
}
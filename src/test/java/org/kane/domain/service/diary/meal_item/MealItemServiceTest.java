package org.kane.domain.service.diary.meal_item;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.Product;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.enum_types.NutritionType;
import org.kane.database.repository.diary.meal.MealRepository;
import org.kane.database.repository.diary.meal_item.MealItemRepository;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.meal_item.MealItemEditDTO;
import org.kane.domain.service.diary.meal.MealService;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

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
class MealItemServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private MealItemService mealItemService;
    @Autowired
    private MealRepository mealRepository;
    @Autowired
    private MealItemRepository mealItemRepository;

    @Transactional
    @Test
    void unpackMealItem() {
        var meal = mealRepository.findById(3L).orElseThrow();
        var micdto = MealItemCreateDTO.builder()
                .nutritionID(4L)
                .nutritionType(NutritionType.RECIPE)
                .weight(new ProductWeight(200.0))
                .build();
        assertThat(meal.getMealItems()).as("oldMealSize").isNotNull().hasSize(1);
        mealItemService.unpackMealItem(micdto,  meal);
        var newMeal = mealRepository.findById(3L).orElseThrow();
        assertThat(newMeal.getMealItems()).as("NewMealSize").isNotNull().hasSize(2);
        var mealItem = newMeal.getMealItems().get(1);
        assertThat(mealItem).isNotNull();
        assertThat(mealItem.getId()).isEqualTo(9L);
        assertThat(mealItem.getMeal().getId()).isEqualTo(meal.getId());
        assertThat(mealItem.getMeal().getType()).isEqualTo(meal.getType());
        assertThat(mealItem.getProductWeight()).isEqualTo(micdto.getWeight());
        assertThat(mealItem.getNutritionalInfo().getId()).isEqualTo(micdto.getNutritionID());
        assertThat(mealItem.getNutritionalInfo()).isInstanceOf(Recipe.class).isNotInstanceOf(Product.class);
    }

    @Transactional
    @Test
    void unpackMealItem1() {
        var meal = mealRepository.findById(3L).orElseThrow();
        var micdto = MealItemCreateDTO.builder()
                .nutritionID(3L)
                .nutritionType(NutritionType.PRODUCT)
                .weight(new ProductWeight(200.0))
                .build();
        assertThat(meal.getMealItems()).as("oldMealSize").isNotNull().hasSize(1);
        mealItemService.unpackMealItem(micdto,  meal);
        var newMeal = mealRepository.findById(3L).orElseThrow();
        assertThat(newMeal.getMealItems()).as("NewMealSize").isNotNull().hasSize(2);
        var mealItem = newMeal.getMealItems().get(1);
        assertThat(mealItem).isNotNull();
        assertThat(mealItem.getId()).isEqualTo(9L);
        assertThat(mealItem.getMeal().getId()).isEqualTo(meal.getId());
        assertThat(mealItem.getMeal().getType()).isEqualTo(meal.getType());
        assertThat(mealItem.getProductWeight()).isEqualTo(micdto.getWeight());
        assertThat(mealItem.getNutritionalInfo().getId()).isEqualTo(micdto.getNutritionID());
        assertThat(mealItem.getNutritionalInfo()).isInstanceOf(Product.class).isNotInstanceOf(Recipe.class);
    }

    @Transactional
    @Test
    void updateMealItem() {
        var miedto = MealItemEditDTO.builder()
                .id(8L)
                .nutritionID(9L)
                .nutritionType(NutritionType.RECIPE)
                .weight(new ProductWeight(500.0))
                .build();
        var meal = mealItemRepository.findById(miedto.getId()).orElseThrow();
        assertThat(meal).isNotNull();
        mealItemService.updateMealItem(miedto);
        var newMeal = mealItemRepository.findById(miedto.getId()).orElseThrow();
        assertThat(newMeal).isNotNull();
        assertThat(newMeal.getId()).isEqualTo(miedto.getId());
        assertThat(newMeal.getProductWeight()).isEqualTo(miedto.getWeight());
        assertThat(newMeal.getNutritionalInfo().getId()).isEqualTo(miedto.getNutritionID());
        assertThat(newMeal.getNutritionalInfo().getDiscriminator()).isEqualTo(NutritionType.RECIPE.toString());
    }

    @Transactional
    @Test
    void updateMealItem2() {
        var miedto = MealItemEditDTO.builder()
                .id(8L)
                .weight(new ProductWeight(500.0))
                .build();
        var meal = mealItemRepository.findById(miedto.getId()).orElseThrow();
        assertThat(meal).isNotNull();
        mealItemService.updateMealItem(miedto);
        var newMeal = mealItemRepository.findById(miedto.getId()).orElseThrow();
        assertThat(newMeal).isNotNull();
        assertThat(newMeal.getId()).isEqualTo(miedto.getId());
        assertThat(newMeal.getProductWeight()).isEqualTo(miedto.getWeight());
        assertThat(newMeal.getNutritionalInfo().getId()).isEqualTo(meal.getNutritionalInfo().getId());
        assertThat(newMeal.getNutritionalInfo().getDiscriminator()).isEqualTo(meal.getNutritionalInfo().getDiscriminator());
    }

    @Test
    void deleteMealItem(){
        var mealItem = mealItemRepository.findById(3L).orElseThrow();
        assertThat(mealItem).isNotNull();
        mealItemService.deleteMealItem(mealItem.getId());
        mealItem = mealItemRepository.findById(3L).orElse(null);
        assertThat(mealItem).isNull();

    }
}
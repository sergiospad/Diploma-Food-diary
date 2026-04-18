package org.kane.integration;

import org.kane.database.entity.Product;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.User;
import org.kane.database.entity.diary.DiaryRecord;
import org.kane.database.entity.physical_quantity.ProductWeight;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.entity.recipe_recource.*;
import org.kane.database.enum_types.Gender;
import org.kane.database.enum_types.ImageType;
import org.kane.database.enum_types.Role;
import org.springframework.boot.test.context.TestComponent;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@TestComponent
public class SavedEntities {

    public User getUser(){
        return User.builder()
                .id(1L)
                .username("john_doe")
                .password("$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy")
                .email("john@example.com")
                .height((short) 175)
                .birthdate(LocalDate.of(1990,5, 15))
                .role(Role.USER)
                .gender(Gender.M)
                .createdAt(LocalDateTime.of(2024, 1, 10, 10, 0, 0))
                .build();
    }

    public Recipe getRecipe(){
        var recipe = Recipe.builder()
                .summary("Наваристый куриный суп с лапшой и овощами")
                .createdAt(LocalDateTime.of(2024, 1, 15, 12, 0 , 0))
                .cookingTime((short) 60)
                .build();
        recipe.setId(4L);
        recipe.setName("Куриный суп");
        recipe.setCalories(new Calories(45.0));
        recipe.setProtein(new Protein(4.5));
        recipe.setFat(new Fat(1.5));
        recipe.setCarbs(new Carbs(4.0));
        recipe.setIsPrivate(Boolean.FALSE);
        return recipe;
    }

    public ImageModel getAvatar(){
        return ImageModel.builder()
                .id(1L)
                .url(Path.of("/images/avatars/default.jpg"))
                .imageType(ImageType.USER)
                .build();
    }
    
    public ImageModel getIllustration(){
        return ImageModel.builder()
                .id(3L)
                .url(Path.of("/images/recipes/salad.jpg"))
                .imageType(ImageType.RECIPE)
                .build();
    }

    public Category getCategory(){
        return Category.builder()
                .id(1L)
                .name("Мясо")
                .build();
    }

    public Product getProduct(){
        var product = Product.builder()
                .description("Куриная грудка без кожи и костей")
                .build();
        product.setId(1L);
        product.setName("Куриная грудка");
        product.setCalories(new Calories(165.0));
        product.setProtein(new Protein(31.00));
        product.setFat(new Fat(3.60));
        product.setCarbs(new Carbs(0.00));
        product.setIsPrivate(Boolean.FALSE);
        return product;
    }

    public Coefficient getCoefficient(){
        return Coefficient.builder()
                .id(1L)
                .conversionFactor(100.0000)
                .build();
    }

    public MeasureUnit getMeasureUnit(){
        return MeasureUnit.builder()
                .id(1L)
                .name("г")
                .build();
    }

    public List<CookingStage> getCookingStages(){
        return List.of(
                CookingStage.builder()
                        .id(1L)
                        .stageNumber((short)1)
                        .description("Нарежьте курицу и овощи")
                        .build(),
                CookingStage.builder()
                        .id(2L)
                        .stageNumber((short)2)
                        .description("Залейте водой и варите 30 минут")
                        .build(),
                CookingStage.builder()
                        .id(3L)
                        .stageNumber((short)3)
                        .description("Добавьте лапшу и варите ещё 10 минут")
                        .build()
        );
    }
    public Ingredient getIngredient(){
        return Ingredient.builder()
                .id(1L)
                .weight(new ProductWeight(200.0))
                .build();
    }

    public DiaryRecord getDiaryRecord(){
        return DiaryRecord.builder()
                .id(1L)
                .recordDate(LocalDate.of(2024, 1, 15))
                .autoCalculation(true)
                .build();
    }
}

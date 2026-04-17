package org.kane.integration;

import org.kane.database.entity.Recipe;
import org.kane.database.entity.User;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.enum_types.Gender;
import org.kane.database.enum_types.ImageType;
import org.kane.database.enum_types.Role;
import org.springframework.boot.test.context.TestComponent;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
}

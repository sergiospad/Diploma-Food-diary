package org.kane.domain.mappers.recipe;

import org.kane.database.entity.Recipe;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.domain.DTO.entityDTO.recipe.RecipeCreateDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CreateRecipeMapper implements Mapper<RecipeCreateDTO, Recipe> {

    @Override
    public Recipe map(RecipeCreateDTO from) {
        Recipe recipe = Recipe.builder()
                .summary(from.getSummary())
                .cookingTime(from.getCookingTime())
                .build();
        recipe.setName(from.getName());
        recipe.setIsPrivate(from.getIsPrivate());
        recipe.setCalories(new Calories(0.0));
        recipe.setProtein(new Protein(0.0));
        recipe.setFat(new Fat(0.0));
        recipe.setCarbs(new Carbs(0.0));
        return recipe;
    }
}

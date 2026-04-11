package org.kane.domain.mappers.recipe;

import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipeCreateDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CreateRecipeMapper implements Mapper<RecipeCreateDTO, Recipe> {

    @Override
    public Recipe map(RecipeCreateDTO from) {
        Recipe recipe = Recipe.builder()
                .summary(from.getName())
                .cookingTime(from.getCookingTime())
                .build();
        recipe.setName(from.getName());
        recipe.setIsPrivate(from.getIsPrivate());
        return recipe;
    }
}

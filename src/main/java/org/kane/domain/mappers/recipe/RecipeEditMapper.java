package org.kane.domain.mappers.recipe;

import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipeEditDTO;
import org.kane.domain.mappers.CopyMapper;
import org.springframework.stereotype.Component;

@Component
public class RecipeEditMapper implements CopyMapper<RecipeEditDTO, Recipe> {
    @Override
    public Recipe copyMap(RecipeEditDTO from, Recipe to) {
        to.setIsPrivate(from.getIsPrivate());
        to.setSummary(from.getSummary());
        to.setName(from.getName());
        to.setCookingTime(from.getCookingTime());
        return to;
    }
}

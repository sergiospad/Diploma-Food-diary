package org.kane.domain.mappers.recipe;

import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipeEditDTO;
import org.kane.domain.mappers.CopyMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecipeEditMapper implements CopyMapper<RecipeEditDTO, Recipe> {
    @Override
    public Recipe copyMap(RecipeEditDTO from, Recipe to) {
        to.setIsPrivate(Optional.ofNullable(from.getIsPrivate()).orElse(to.getIsPrivate()));
        to.setSummary(Optional.ofNullable(from.getSummary()).orElse(to.getSummary()));
        to.setName(Optional.ofNullable(from.getName()).orElse(to.getName()));
        to.setCookingTime(Optional.ofNullable(from.getCookingTime()).orElse(to.getCookingTime()));
        to.setIsPrivate(Optional.ofNullable(from.getIsPrivate()).orElse(to.getIsPrivate()));
        return to;
    }
}

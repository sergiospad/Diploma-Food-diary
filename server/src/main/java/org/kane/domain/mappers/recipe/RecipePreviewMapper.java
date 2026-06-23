package org.kane.domain.mappers.recipe;

import org.kane.database.entity.Recipe;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RecipePreviewMapper implements Mapper<Recipe, RecipePreviewDTO> {
    @Override
    public RecipePreviewDTO map(Recipe from) {
        Long imageId = Optional.ofNullable(from.getIllustration())
                .map(ImageModel::getId)
                .orElse(null);
        return RecipePreviewDTO.builder()
                .id(from.getId())
                .name(from.getName())
                .summary(from.getSummary())
                .imageID(imageId)
                .build();
    }
}

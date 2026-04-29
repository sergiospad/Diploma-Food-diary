package org.kane.domain.mappers.recipe;

import org.kane.domain.DTO.entityDTO.recipe.RecipePreShowProjection;
import org.kane.domain.DTO.entityDTO.recipe.RecipeShowDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class PreShowToShowMapper implements Mapper<RecipePreShowProjection, RecipeShowDTO> {
    @Override
    public RecipeShowDTO map(RecipePreShowProjection from) {
        return RecipeShowDTO.builder()
                .id(from.getId())
                .authorName(from.getAuthorName())
                .avatarID(from.getAvatarID())
                .cookingTime(from.getCookingTime())
                .name(from.getName())
                .summary(from.getSummary())
                .illustrationID(from.getIllustrationID())
                .createdAt(from.getCreatedAt())
                .build();
    }
}

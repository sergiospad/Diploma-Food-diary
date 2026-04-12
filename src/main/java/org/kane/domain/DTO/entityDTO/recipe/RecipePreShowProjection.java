package org.kane.domain.DTO.entityDTO.recipe;

import lombok.Data;

@Data
public class RecipePreShowProjection {
    Long id;
    String authorName;
    Long avatarID;
    Short cookingTime;
    String name;
    String summary;
    Long illustrationID;
}

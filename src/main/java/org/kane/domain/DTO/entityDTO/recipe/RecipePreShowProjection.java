package org.kane.domain.DTO.entityDTO.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecipePreShowProjection {
    Long id;
    String authorName;
    Long avatarID;
    Short cookingTime;
    String name;
    String summary;
    Long illustrationID;
}

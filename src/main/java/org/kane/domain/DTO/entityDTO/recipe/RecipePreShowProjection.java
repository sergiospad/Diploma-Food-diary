package org.kane.domain.DTO.entityDTO.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

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
    LocalDateTime createdAt;
}

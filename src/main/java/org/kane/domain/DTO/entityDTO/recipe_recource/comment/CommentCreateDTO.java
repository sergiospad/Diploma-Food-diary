package org.kane.domain.DTO.entityDTO.recipe_recource.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCreateDTO {
    private String message;
    private Long recipeID;
    private Long imageID;
}

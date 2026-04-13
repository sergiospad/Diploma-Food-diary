package org.kane.domain.DTO.entityDTO.recipe_recource.comment;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String message;
    private Long productID;
    private Long imageID;
}

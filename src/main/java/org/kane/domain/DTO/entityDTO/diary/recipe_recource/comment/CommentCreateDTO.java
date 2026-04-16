package org.kane.domain.DTO.entityDTO.diary.recipe_recource.comment;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String message;
    private Long productID;
    private Long imageID;
}

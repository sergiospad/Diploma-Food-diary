package org.kane.domain.DTO.entityDTO.comment;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private String message;
    private Long productID;
    private Long imageID;
}

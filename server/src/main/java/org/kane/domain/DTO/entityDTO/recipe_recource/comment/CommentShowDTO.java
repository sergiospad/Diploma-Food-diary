package org.kane.domain.DTO.entityDTO.recipe_recource.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentShowDTO {
    private Long id;
    private String username;
    private Long userAvatarID;
    private String message;
    private LocalDateTime createdAt;
    private Long imageID;
}

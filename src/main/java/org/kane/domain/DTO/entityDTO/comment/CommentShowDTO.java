package org.kane.domain.DTO.entityDTO.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentShowDTO {
    private Long id;
    private String username;
    private Long userAvatarID;
    private String message;
    private LocalDateTime createdAt;
    private Long imageID;
}

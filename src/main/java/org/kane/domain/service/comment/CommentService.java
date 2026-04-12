package org.kane.domain.service.comment;

import org.kane.database.entity.recipe_recource.Comment;
import org.kane.domain.DTO.entityDTO.comment.CommentCreateDTO;
import org.kane.domain.DTO.entityDTO.comment.CommentShowDTO;

import java.security.Principal;

public interface CommentService {
    CommentShowDTO createComment(Principal principal, CommentCreateDTO commentCreateDTO);
}

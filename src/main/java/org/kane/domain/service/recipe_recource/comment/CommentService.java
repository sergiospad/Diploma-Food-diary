package org.kane.domain.service.recipe_recource.comment;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.comment.CommentCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.comment.CommentShowDTO;

import java.security.Principal;

public interface CommentService {
    CommentShowDTO createComment(Principal principal, CommentCreateDTO commentCreateDTO);
}

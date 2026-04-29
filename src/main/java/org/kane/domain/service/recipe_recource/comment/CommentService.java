package org.kane.domain.service.recipe_recource.comment;

import org.kane.domain.DTO.entityDTO.recipe_recource.comment.CommentCreateDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.comment.CommentShowDTO;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    CommentShowDTO createComment(Principal principal, CommentCreateDTO commentCreateDTO);

    void deleteComment(Principal principal, Long id);

    List<CommentShowDTO> getByRecipeID(Long recipeID);
}

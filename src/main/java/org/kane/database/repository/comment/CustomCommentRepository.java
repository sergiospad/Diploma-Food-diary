package org.kane.database.repository.comment;

import org.kane.domain.DTO.entityDTO.comment.CommentShowDTO;

import java.util.List;

public interface CustomCommentRepository {
    List<CommentShowDTO> getAllShowDTOFromRecipeID(Long recipeID);
}

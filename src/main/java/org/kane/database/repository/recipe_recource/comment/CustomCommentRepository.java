package org.kane.database.repository.recipe_recource.comment;

import org.kane.domain.DTO.entityDTO.diary.recipe_recource.comment.CommentShowDTO;

import java.util.List;

public interface CustomCommentRepository {
    List<CommentShowDTO> getAllShowDTOFromRecipeID(Long recipeID);
}

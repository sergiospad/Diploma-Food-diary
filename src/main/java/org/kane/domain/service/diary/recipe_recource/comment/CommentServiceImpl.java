package org.kane.domain.service.diary.recipe_recource.comment;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.recipe_recource.Comment;
import org.kane.database.repository.recipe_recource.comment.CommentRepository;
import org.kane.database.repository.recipe_recource.image_model.ImageModelRepository;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.comment.CommentCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.comment.CommentShowDTO;
import org.kane.domain.mappers.CommentMapperShow;
import org.kane.exceptions.not_found.RecipeNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ImageModelRepository imageModelRepository;
    private final CommentMapperShow commentMapperShow;

    @Override
    public CommentShowDTO createComment(Principal principal, CommentCreateDTO commentCreateDTO){
        var user = userRepository.getCurrentUser(principal);
        var recipe = recipeRepository.findById(commentCreateDTO.getProductID())
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
        var image = imageModelRepository.findById(commentCreateDTO.getImageID())
                .orElse(null);
        var comment = Comment.builder()
                .image(image)
                .message(commentCreateDTO.getMessage())
                .commentator(user)
                .recipe(recipe)
                .build();
        comment =  commentRepository.save(comment);
        return commentMapperShow.map(comment);
    }


}

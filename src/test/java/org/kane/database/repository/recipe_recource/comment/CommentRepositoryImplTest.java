package org.kane.database.repository.recipe_recource.comment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.User;
import org.kane.database.entity.recipe_recource.Comment;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/recipe_resource/data-comment-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CommentRepositoryImplTest extends IntegrationTestBase {

    private Comment savedComment;
    private User savedUser;
    private Recipe savedRecipe;
    private ImageModel  savedImageModel;
    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private CommentRepository commentRepository;
    private ImageModel avatar;

    @BeforeEach
    void setUp() {
            avatar = savedEntities.getAvatar();
            savedRecipe = savedEntities.getRecipe();
            savedUser = savedEntities.getUser();
            savedUser.setAvatar(avatar);
            savedImageModel = ImageModel.builder().id(3L).build();
            savedComment = Comment.builder()
                    .id(1L)
                    .message("Отличный рецепт, очень вкусно!")
                    .createdAt(LocalDateTime.of(2024, 1, 20, 10, 0,0))
                    .commentator(savedUser)
                    .recipe(savedRecipe)
                    .image(savedImageModel)
                    .build();
    }

    @Test
    void findById() {
        var comment = commentRepository.findById(savedComment.getId())
                .orElseThrow(()-> new RuntimeException(" "));
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(savedComment.getId());
        assertThat(comment.getMessage()).isEqualTo(savedComment.getMessage());
        assertThat(comment.getCreatedAt()).isEqualTo(savedComment.getCreatedAt());
        assertThat(comment.getRecipe().getId()).isEqualTo(savedRecipe.getId());
        assertThat(comment.getImage().getId()).isEqualTo(savedImageModel.getId());
        assertThat(comment.getCommentator().getId()).isEqualTo(savedComment.getCommentator().getId());
    }

    @Test
    void getAllShowDTOFromRecipeID() {
        var comments = commentRepository.getAllShowDTOFromRecipeID(savedRecipe.getId());
        assertThat(comments).isNotNull().hasSize(2);
        var comment =  comments.getLast();
        assertThat(comment.getId()).isEqualTo(savedComment.getId());
        assertThat(comment.getMessage()).isEqualTo(savedComment.getMessage());
        assertThat(comment.getCreatedAt()).isEqualTo(savedComment.getCreatedAt());
        assertThat(comment.getUserAvatarID()).isEqualTo(savedComment.getCommentator().getAvatar().getId());
        assertThat(comment.getUsername()).isEqualTo(savedComment.getCommentator().getUsername());
    }

}
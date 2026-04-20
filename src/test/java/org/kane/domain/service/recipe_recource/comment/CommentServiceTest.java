package org.kane.domain.service.recipe_recource.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.repository.recipe_recource.comment.CommentRepository;
import org.kane.domain.DTO.entityDTO.recipe_recource.comment.CommentCreateDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;

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
class CommentServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    private Principal principal;

    @BeforeEach
    void setUp() {
        principal = new Principal() {
            @Override
            public String getName() {
                return "admin";
            }
        };
    }

    @Test
    void createComment() {
        var commentCreateDTO = CommentCreateDTO.builder()
                .message("testCommend")
                .recipeID(4L)
                .imageID(1L)
                .build();
        var comment = commentService.createComment(principal, commentCreateDTO);
        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(6L);
        assertThat(comment.getMessage()).isEqualTo(commentCreateDTO.getMessage());
        assertThat(comment.getUsername()).isEqualTo(principal.getName());
        assertThat(comment.getImageID()).isEqualTo(commentCreateDTO.getImageID());
        assertThat(comment.getUserAvatarID()).isEqualTo(1L);
    }

    @Test
    void deleteComment(){
        var comments = commentRepository.getAllShowDTOFromRecipeID(4L);
        assertThat(comments).isNotNull().hasSize(2);
        commentService.deleteComment(principal, 1L);
        comments = commentRepository.getAllShowDTOFromRecipeID(4L);
        assertThat(comments).isNotNull().hasSize(1);
    }
}
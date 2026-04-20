package org.kane.database.repository.recipe_recource.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.recipe_recource.comment.CommentShowDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QRecipe.recipe;
import static org.kane.database.entity.QUser.user;
import static org.kane.database.entity.recipe_recource.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentShowDTO> getAllShowDTOFromRecipeID(Long recipeID) {
        return queryFactory.select(Projections.constructor(CommentShowDTO.class,
                    comment.id,
                    user.username,
                    user.avatar.id.as("userAvatarID"),
                    comment.message,
                    comment.createdAt,
                    Expressions.asNumber(
                          Expressions.cases()
                               .when(comment.image.isNotNull())
                               .then(comment.image.id)
                               .otherwise((Long) null)
                    ).as("imageId")))
                .from(recipe)
                .join(recipe.comments, comment)
                .join(recipe.author, user)
                .where(recipe.id.eq(recipeID))
                .orderBy(comment.createdAt.desc())
                .distinct()
                .fetch();
    }
}

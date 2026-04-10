package org.kane.database.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.kane.database.entity.QRecipe;
import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.beans.Expression;
import java.util.List;

import static org.kane.database.entity.QRecipe.recipe;

@Repository
@RequiredArgsConstructor
public class CustomRecipeRepositoryImpl implements CustomRecipeRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<RecipePreviewDTO> findAllPreviewDTO(BooleanBuilder predicate, Pageable pageable) {

        long total = queryFactory.select(recipe.id.count()).from(recipe).where(predicate).fetchOne();

        List<RecipePreviewDTO> content = queryFactory.select(Projections.constructor(RecipePreviewDTO.class,
                recipe.id,
                recipe.name,
                recipe.summary,
                recipe.illustration.id.as("imageID")
                ))
                .from(recipe)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }
}

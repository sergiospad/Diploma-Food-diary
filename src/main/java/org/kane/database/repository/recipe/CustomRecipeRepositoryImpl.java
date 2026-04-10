package org.kane.database.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.search.mapper.orm.Search;
import org.kane.database.entity.QRecipe;
import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeSummarySearchDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeTitleSearchDTO;
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
    private final EntityManager em;

    @Override
    public Page<RecipePreviewDTO> findAllPreviewDTO(BooleanBuilder predicate, Pageable pageable) {

        long total = new JPAQuery<Long>(em).select(recipe.id.count()).from(recipe).where(predicate).fetchOne();

        List<RecipePreviewDTO> content = new JPAQuery<RecipePreviewDTO>()
                .select(Projections.constructor(RecipePreviewDTO.class,
                    recipe.id,
                    recipe.name,
                    recipe.summary,
                    recipe.illustration.id.as("imageID")))
                .from(recipe)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<RecipeSummarySearchDTO> findSummaryDTOByItem(String searchItem) {
        return Search.session(em).search(RecipeSummarySearchDTO.class)
                .where(f->f.match()
                        .field("summary")
                        .matching(searchItem)
                        .fuzzy(2))
                .fetchHits(5);
    }

    @Override
    public List<RecipeTitleSearchDTO> findTitleDTOByItem(String searchItem) {
        return Search.session(em).search(RecipeTitleSearchDTO.class)
                .where(f->f.match()
                        .field("name")
                        .matching(searchItem)
                        .fuzzy(2)
                ).fetchHits(5);
    }


}

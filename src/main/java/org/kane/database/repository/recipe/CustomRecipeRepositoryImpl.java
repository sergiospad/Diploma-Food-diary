package org.kane.database.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.jspecify.annotations.NonNull;
import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreShowProjection;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeSummarySearchDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeTitleSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.kane.database.entity.QRecipe.recipe;
import static org.kane.database.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomRecipeRepositoryImpl implements CustomRecipeRepository {
    private final EntityManager em;

    @Override
    public Page<RecipePreviewDTO> findAllPreviewDTOOrderedByNew(BooleanBuilder predicate, Pageable pageable) {
        OrderSpecifier<LocalDateTime> order = recipe.createdAt.desc();
        return getRecipePreviewDTOs(predicate, pageable, order);
    }

    private @NonNull PageImpl<RecipePreviewDTO> getRecipePreviewDTOs(BooleanBuilder predicate, Pageable pageable, OrderSpecifier<LocalDateTime> order) {
        long total = new JPAQuery<Long>(em).select(recipe.id.count()).from(recipe).where(predicate).fetchOne();

        List<RecipePreviewDTO> content = new JPAQuery<RecipePreviewDTO>(em)
                .select(Projections.constructor(RecipePreviewDTO.class,
                    recipe.id,
                    recipe.name,
                    recipe.summary,
                    recipe.illustration.id.as("imageID")))
                .from(recipe)
                .where(predicate)
                .orderBy(order)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<RecipePreviewDTO> findAllPreviewDTOOrderedByOlder(BooleanBuilder predicate, Pageable pageable) {
        OrderSpecifier<LocalDateTime> order = recipe.createdAt.asc();

        return getRecipePreviewDTOs(predicate, pageable, order);
    }

    @Override
    public List<RecipeSummarySearchDTO> findSummaryDTOByItem(String searchItem) {
        return Search.session(em).search(Recipe.class)
                .where(f->f.match()
                        .field("summary")
                        .matching(searchItem)
                        .fuzzy(2))
                .fetchHits(5)
                .stream()
                .map(recipe -> new RecipeSummarySearchDTO(
                        recipe.getId(),
                        recipe.getName(),
                        recipe.getSummary()))
                .toList();
    }


    @Override
    public List<RecipeTitleSearchDTO> findTitleDTOByItem(String searchItem) {
        return Search.session(em).search(Recipe.class)
                .where(f->f.match()
                        .field("name")
                        .matching(searchItem)
                        .fuzzy(2)
                ).fetchHits(5)
                .stream().map(
                        rec-> new RecipeTitleSearchDTO(
                                rec.getId(),
                                rec.getName()))
                .toList();
    }


    @Override
    public RecipePreShowProjection getRecipePreShowProjByID(Long recipeID){
        return new JPAQuery<RecipePreShowProjection>(em).select(Projections.constructor(RecipePreShowProjection.class,
                    recipe.id,
                    user.username.as("authorName"),
                    user.avatar.id.as("avatarID"),
                    recipe.cookingTime,
                    recipe.name,
                    recipe.summary.as("summary"),
                    recipe.illustration.id.as("illustrationID")))
                .from(recipe)
                .join(recipe.author, user)
                .where(recipe.id.eq(recipeID))
                .distinct()
                .fetchOne();
    }
}

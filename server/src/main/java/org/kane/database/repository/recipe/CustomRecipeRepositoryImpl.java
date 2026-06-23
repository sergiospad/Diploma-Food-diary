package org.kane.database.repository.recipe;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.util.common.SearchException;
import org.jspecify.annotations.NonNull;
import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreShowProjection;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeSummarySearchDTO;
import org.kane.domain.DTO.entityDTO.recipe.RecipeTitleSearchDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.FavouriteRecipeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QRecipe.recipe;
import static org.kane.database.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class CustomRecipeRepositoryImpl implements CustomRecipeRepository {
    private final EntityManager em;
    private volatile boolean recipeReindexAttempted;

    @Override
    public Page<RecipePreviewDTO> findAllPreviewDTOOrderedByNew(BooleanBuilder predicate, Pageable pageable) {
        return getRecipePreviewDTOs(predicate, pageable, recipe.createdAt.desc());
    }

    private @NonNull PageImpl<RecipePreviewDTO> getRecipePreviewDTOs(
            BooleanBuilder predicate,
            Pageable pageable,
            OrderSpecifier<?>... orders) {
        long total = new JPAQuery<Long>(em).select(recipe.id.count()).from(recipe).where(predicate).fetchOne();

        List<RecipePreviewDTO> content = new JPAQuery<RecipePreviewDTO>(em)
                .select(Projections.constructor(RecipePreviewDTO.class,
                    recipe.id,
                    recipe.name,
                    recipe.summary,
                    recipe.illustration.id.as("imageID")))
                .from(recipe)
                .where(predicate)
                .orderBy(orders)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<RecipePreviewDTO> findAllPreviewDTOOrderedByOlder(BooleanBuilder predicate, Pageable pageable) {
        return getRecipePreviewDTOs(predicate, pageable, recipe.createdAt.asc());
    }

    @Override
    public Page<RecipePreviewDTO> findAllPreviewDTOOrderedByPopular(BooleanBuilder predicate, Pageable pageable) {

        NumberExpression<Long> favouriteUsersCount = Expressions.numberTemplate(Long.class,
                "(select count(favUser) from User favUser join favUser.favouriteRecipes favRecipe where favRecipe.id = {0})",
                recipe.id);
        return getRecipePreviewDTOs(predicate, pageable, favouriteUsersCount.desc(), recipe.createdAt.desc());
    }

    @Override
    public List<RecipeSummarySearchDTO> findSummaryDTOByItem(String searchItem) {
        List<RecipeSummarySearchDTO> searchHits;
        for (int i = 0; i<3; i++){
            searchHits = findSummaryWithHibernateSearch(searchItem);
            if (searchHits.isEmpty())
                reindexRecipesIfNeeded();
            else return searchHits;
        }
        return new JPAQuery<RecipeSummarySearchDTO>(em)
                .select(Projections.constructor(RecipeSummarySearchDTO.class,
                        recipe.id,
                        recipe.name,
                        recipe.summary))
                .from(recipe)
                .where(
                        recipe.summary.containsIgnoreCase(searchItem)
                                .or(recipe.summary.containsIgnoreCase(searchItem))
                                .and(recipe.isPrivate.isNull().or(recipe.isPrivate.isFalse()))
                )
                .limit(5)
                .fetch();
    }


    @Override
    public List<RecipeTitleSearchDTO> findTitleDTOByItem(String searchItem) {
        List<RecipeTitleSearchDTO> searchHits;
        for (int i = 0; i<3; i++){
            searchHits = findTitleWithHibernateSearch(searchItem);
            if (searchHits.isEmpty())
                reindexRecipesIfNeeded();
            else return searchHits;
        }
        return new JPAQuery<RecipeTitleSearchDTO>(em)
                .select(Projections.constructor(RecipeTitleSearchDTO.class,
                        recipe.id,
                        recipe.name))
                .from(recipe)
                .where(
                        recipe.name.containsIgnoreCase(searchItem)
                                .or(recipe.name.containsIgnoreCase(searchItem))
                                .and(recipe.isPrivate.isNull().or(recipe.isPrivate.isFalse()))
                )
                .limit(5)
                .fetch();
    }

    private List<RecipeTitleSearchDTO> findTitleWithHibernateSearch(String searchItem) {
        return Search.session(em).search(Recipe.class)
                .where(f->f.match()
                        .field("name")
                        .matching(searchItem)
                        .fuzzy(2)
                ).fetchHits(5)
                .stream()
                .filter(r -> !Boolean.TRUE.equals(r.getIsPrivate()))
                .map(rec -> new RecipeTitleSearchDTO(rec.getId(), rec.getName()))
                .toList();
    }

    private List<RecipeSummarySearchDTO> findSummaryWithHibernateSearch(String searchItem) {
        return Search.session(em).search(Recipe.class)
                .where(f->f.match()
                        .field("summary")
                        .matching(searchItem)
                        .fuzzy(2))
                .fetchHits(5)
                .stream()
                .filter(r -> !Boolean.TRUE.equals(r.getIsPrivate()))
                .map(rec -> new RecipeSummarySearchDTO(rec.getId(), rec.getName(), rec.getSummary()))
                .toList();
    }

    private synchronized void reindexRecipesIfNeeded() {
        if (recipeReindexAttempted) {
            return;
        }
        try {
            Search.session(em).massIndexer(Recipe.class).startAndWait();
            recipeReindexAttempted = true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while rebuilding recipe search index", ex);
        } catch (SearchException ex) {
            // Keep fallback path available if index cannot be rebuilt right now.
        }
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
                    recipe.illustration.id.as("illustrationID"),
                    recipe.createdAt))
                .from(recipe)
                .join(recipe.author, user)
                .where(recipe.id.eq(recipeID))
                .distinct()
                .fetchOne();
    }
    @Override
    public Long getAuthorIDByRecipeID(Long recipeID){
        return new JPAQuery<Long>(em).select(recipe.author.id)
                .from(recipe)
                .join(recipe.author, user)
                .where(recipe.id.eq(recipeID))
                .fetchOne();
    }

    @Override
    public List<Long> checkForFavourites(Long userID, List<Long> recipesID){
        return new JPAQuery<Long>(em).select(recipe.id)
                .from(user)
                .join(user.favouriteRecipes, recipe)
                .where(recipe.id.in(recipesID).and(user.id.eq(userID)))
                .fetch();
    }

}

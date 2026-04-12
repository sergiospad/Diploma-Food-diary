package org.kane.database.repository.ingredient;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientEnergyDTO;
import org.kane.domain.DTO.entityDTO.ingredient.IngredientPreShowProjection;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QRecipe.recipe;
import static org.kane.database.entity.recipe_recource.QIngredient.ingredient;

@Repository
@RequiredArgsConstructor
public class CustomIngredientRepositoryImpl implements CustomIngredientRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<IngredientPreShowProjection> findPreShowDTO(Long recipeID) {
        return queryFactory.select(Projections.constructor(IngredientPreShowProjection.class,
                    ingredient.id,
                    ingredient.product.id,
                    ingredient.weight,
                    ingredient.specMeasureUnit.id))
                .from(ingredient)
                .where(ingredient.recipe.id.eq(recipeID))
                .fetch();
    }

    @Override
    public IngredientPreShowProjection findPreShowDTOById(Long ingredientID) {
        return queryFactory.select(Projections.constructor(IngredientPreShowProjection.class,
                        ingredient.id,
                        ingredient.product.id,
                        ingredient.weight,
                        ingredient.specMeasureUnit.id))
                .from(ingredient)
                .where(ingredient.id.eq(ingredientID))
                .fetchOne();
    }

    @Override
    public List<IngredientEnergyDTO> findIngredientEnergyDTOByRecipeID(Long recipeID){
        return queryFactory.select(Projections.constructor(IngredientEnergyDTO.class,
                    ingredient.id,
                    ingredient.weight,
                    ingredient.product.calories,
                    ingredient.product.protein,
                    ingredient.product.fat,
                    ingredient.product.carbs))
                .from(recipe)
                .join(recipe.ingredients, ingredient)
                .where(recipe.id.eq(recipeID))
                .fetch();
    }


}

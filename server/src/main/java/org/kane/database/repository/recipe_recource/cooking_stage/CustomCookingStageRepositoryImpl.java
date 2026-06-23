package org.kane.database.repository.recipe_recource.cooking_stage;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.recipe_recource.cooking_stage.CookingStageShowDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.recipe_recource.QCookingStage.cookingStage;

@Repository
@RequiredArgsConstructor
public class CustomCookingStageRepositoryImpl implements CustomCookingStageRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CookingStageShowDTO> findAllShowDTOByRecipeID(Long recipeID) {
        return queryFactory.select(Projections.constructor(CookingStageShowDTO.class,
                    cookingStage.id,
                    cookingStage.stageNumber,
                    cookingStage.description,
                    Expressions.asNumber(
                         Expressions.cases()
                                .when(cookingStage.image.isNotNull())
                                .then(cookingStage.image.id)
                                .otherwise((Long) null)
                    ).as("imageId")
                )).from(cookingStage)
                .where(cookingStage.recipe.id.eq(recipeID))
                .orderBy(cookingStage.stageNumber.asc())
                .fetch();
    }
}

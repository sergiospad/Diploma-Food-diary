package org.kane.database.repository.measure_unit;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.measure_unit.MeasureUnitDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QProduct.product;
import static org.kane.database.entity.recipe_recource.QCategory.category;
import static org.kane.database.entity.recipe_recource.QCoefficient.coefficient;
import static org.kane.database.entity.recipe_recource.QIngredient.ingredient;
import static org.kane.database.entity.recipe_recource.QMeasureUnit.measureUnit;

@Repository
@RequiredArgsConstructor
public class CustomMeasureUnitRepositoryImpl implements CustomMeasureUnitRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MeasureUnitDTO> findAllByIngredientID(Long ingredientID) {
        return queryFactory.select(Projections.constructor(MeasureUnitDTO.class,
                    measureUnit.id,
                    measureUnit.name))
                .from(ingredient)
                .join(ingredient.product, product)
                .join(product.category, category)
                .join(category.coefficients, coefficient)
                .join(coefficient.measureUnit, measureUnit)
                .where(ingredient.id.eq(ingredientID))
                .fetch();
    }

    @Override
    public MeasureUnitDTO findByIngredientID(Long ingredientID) {
        return queryFactory.select(Projections.constructor(MeasureUnitDTO.class,
                    measureUnit.id,
                    measureUnit.name))
                .from(ingredient)
                .join(ingredient.specMeasureUnit, measureUnit)
                .where(ingredient.id.eq(ingredientID))
                .fetchOne();
    }
}

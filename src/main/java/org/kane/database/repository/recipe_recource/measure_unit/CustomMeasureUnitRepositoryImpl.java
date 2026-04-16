package org.kane.database.repository.recipe_recource.measure_unit;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.measure_unit.MeasureUnitDTO;
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
                .distinct()
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

    @Override
    public List<MeasureUnitDTO> findAllDistinct() {
        return queryFactory.select(Projections.constructor(MeasureUnitDTO.class,
                        measureUnit.id,
                        measureUnit.name))
                .from(measureUnit)
                .distinct()
                .fetch();
    }

    @Override
    public List<MeasureUnitDTO> findFreeMeasureUnits(Long categoryID){
        var linkedUnits = queryFactory.select(coefficient.measureUnit.id)
                .from(category)
                .join(category.coefficients, coefficient)
                .where(category.id.eq(categoryID))
                .distinct()
                .fetch();
        return queryFactory.select(Projections.constructor(MeasureUnitDTO.class,
                measureUnit.id,
                measureUnit.name)).from(measureUnit)
                .where(measureUnit.id.notIn(linkedUnits))
                .fetch();
    }
}

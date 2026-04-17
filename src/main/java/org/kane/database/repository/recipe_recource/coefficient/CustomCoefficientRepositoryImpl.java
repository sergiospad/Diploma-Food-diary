package org.kane.database.repository.recipe_recource.coefficient;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.coefficient.CoefficientShowDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QProduct.product;
import static org.kane.database.entity.recipe_recource.QCategory.category;
import static org.kane.database.entity.recipe_recource.QCoefficient.coefficient;
import static org.kane.database.entity.recipe_recource.QMeasureUnit.measureUnit;

@Repository
@RequiredArgsConstructor
public class CustomCoefficientRepositoryImpl implements CustomCoefficientRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Double getCoefficientByProductID(Long productID, Long measureUnitID) {
        return queryFactory.select(Projections.constructor(Double.class, coefficient.conversionFactor))
                .from(product)
                .join(product.category,category)
                .join(category.coefficients, coefficient)
                .where(product.id.eq(productID))
                .where(coefficient.measureUnit.id.eq(measureUnitID))
                .fetchOne();
    }

    @Override
    public List<CoefficientShowDTO> getShowDTOByCategoryID(Long categoryID){
        return queryFactory.select(Projections.constructor(CoefficientShowDTO.class,
                    coefficient.id,
                    measureUnit.name,
                    coefficient.conversionFactor
                )).from(category)
                .join(category.coefficients, coefficient)
                .join(coefficient.measureUnit, measureUnit)
                .where(category.id.eq(categoryID))
                .fetch();
    }
}

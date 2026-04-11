package org.kane.database.repository.coefficient;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.recipe_recource.Coefficient;
import org.springframework.stereotype.Repository;

import static org.kane.database.entity.QProduct.product;
import static org.kane.database.entity.recipe_recource.QCoefficient.coefficient;

@Repository
@RequiredArgsConstructor
public class CustomCoefficientRepositoryImpl implements CustomCoefficientRepository{
    private final JPAQueryFactory queryFactory;


    @Override
    public Double getCoefficientByProductID(Long productID, Long measureUnitID) {
        return queryFactory.select(Projections.constructor(Double.class, coefficient.conversionFactor))
                .from(product.category.coefficients.any())
                .where(product.id.eq(productID))
                .where(coefficient.measureUnit.id.eq(measureUnitID))
                .fetchOne();
    }
}

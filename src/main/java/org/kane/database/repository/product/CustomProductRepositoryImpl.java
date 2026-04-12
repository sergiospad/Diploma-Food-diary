package org.kane.database.repository.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.kane.domain.DTO.entityDTO.measure_unit.MeasureUnitDTO;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QProduct.product;
import static org.kane.database.entity.recipe_recource.QMeasureUnit.measureUnit;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final EntityManager em;

    @Override
    public List<ProductSearchDTO> findSearchDTO(String searchItem) {
        return Search.session(em).search(ProductSearchDTO.class)
                .where(f->f.match()
                        .field("name")
                        .matching(searchItem)
                        .fuzzy(2))
                .fetchHits(5);
    }

    @Override
    public List<MeasureUnitDTO> findMeasureUnitDTOByProductId(Long productId) {
        return new JPAQuery<MeasureUnitDTO>(em)
                .select(Projections.constructor(MeasureUnitDTO.class,
                            measureUnit.id,
                            measureUnit.name))
                .from(product.category.coefficients.any().measureUnit)
                .where(product.id.eq(productId))
                .fetch();
    }

    @Override
    public String findNameById(Long id) {
        return new JPAQuery<String>(em).select(Projections.constructor(String.class,
                    product.name))
                .from(product)
                .where(product.id.eq(id))
                .fetchOne();
    }


}

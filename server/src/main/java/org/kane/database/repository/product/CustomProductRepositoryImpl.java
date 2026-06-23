package org.kane.database.repository.product;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.util.common.SearchException;
import org.kane.database.entity.Product;
import org.kane.database.entity.Recipe;
import org.kane.domain.DTO.entityDTO.recipe.RecipeTitleSearchDTO;
import org.kane.domain.DTO.entityDTO.recipe_recource.MeasureUnitDTO;
import org.kane.domain.DTO.entityDTO.nutritional_info.NutritionShowProjection;
import org.kane.domain.DTO.entityDTO.product.ProductSearchDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.QNutritionalInfo.nutritionalInfo;
import static org.kane.database.entity.QProduct.product;
import static org.kane.database.entity.recipe_recource.QCategory.category;
import static org.kane.database.entity.recipe_recource.QCoefficient.coefficient;
import static org.kane.database.entity.recipe_recource.QMeasureUnit.measureUnit;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private volatile boolean productReindexAttempted;


    @Override
    public List<ProductSearchDTO> findSearchDTO(String searchItem) {
        List<ProductSearchDTO> searchHits;
        for (int i = 0; i<3; i++){
            searchHits = findProductWithHibernateSearch(searchItem);
            if (searchHits.isEmpty())
                reindexRecipesIfNeeded();
            else return searchHits;
        }
        return new JPAQuery<ProductSearchDTO>(em)
                .select(Projections.constructor(ProductSearchDTO.class,
                        product.id,
                        product.name
                        )).from(product)
                .where(product.name.eq(searchItem))
                .fetch();
    }

    private synchronized void reindexRecipesIfNeeded() {
        if (productReindexAttempted) {
            return;
        }
        try {
            Search.session(em).massIndexer(Product.class).startAndWait();
            productReindexAttempted = true;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while rebuilding recipe search index", ex);
        } catch (SearchException ex) {
            // Keep fallback path available if index cannot be rebuilt right now.
        }
    }

    private List<ProductSearchDTO> findProductWithHibernateSearch(String searchItem) {
        return Search.session(em).search(Product.class)
                .where(f->f.match()
                        .field("name")
                        .matching(searchItem)
                        .fuzzy(2))
                .fetchHits(5)
                .stream()
                .map(product -> new ProductSearchDTO(product.getId(), product.getName()))
                .toList();
    }

    @Override
    public List<MeasureUnitDTO> findMeasureUnitDTOByProductId(Long productId) {
        return new JPAQuery<MeasureUnitDTO>(em)
                .select(Projections.constructor(MeasureUnitDTO.class,
                            measureUnit.id,
                            measureUnit.name))
                .from(product)
                .join(product.category, category)
                .join(category.coefficients, coefficient)
                .join(coefficient.measureUnit, measureUnit)
                .where(product.id.eq(productId))
                .fetch();
    }

    @Override
    public String findNameById(Long id) {
        return new JPAQuery<String>(em).select(product.name)
                .from(product)
                .where(product.id.eq(id))
                .distinct()
                .fetchOne();
    }


    @Override
    public NutritionShowProjection getNutritionsShowProjection(Long id) {
        return queryFactory.select(Projections.constructor(NutritionShowProjection.class,
                    nutritionalInfo.id,
                    nutritionalInfo.name,
                    nutritionalInfo.calories,
                    nutritionalInfo.protein,
                    nutritionalInfo.fat,
                    nutritionalInfo.carbs,
                    nutritionalInfo
                )).from(nutritionalInfo)
                .where(nutritionalInfo.id.eq(id))
                .distinct()
                .fetchOne();
    }

    @Override
    public Boolean existsByName(String name) {
        return queryFactory
                .selectOne()
                .from(nutritionalInfo)
                .where(nutritionalInfo.name.eq(name))
                .fetchFirst() != null;
    }
}

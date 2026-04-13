package org.kane.database.repository.recipe_recource.category;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.domain.DTO.entityDTO.recipe_recource.category.CategoryNameDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.kane.database.entity.recipe_recource.QCategory.category;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CustomCategoryRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<CategoryNameDTO> findAllCategories() {
        return queryFactory.select(Projections.constructor(CategoryNameDTO.class,
                    category.id,
                    category.name))
                .from(category)
                .orderBy(category.products.size().desc())
                .fetch();
    }
}

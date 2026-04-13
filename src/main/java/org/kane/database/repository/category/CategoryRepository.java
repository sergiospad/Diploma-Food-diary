package org.kane.database.repository.category;

import org.jspecify.annotations.NonNull;
import org.kane.database.entity.recipe_recource.Category;
import org.kane.domain.DTO.entityDTO.category.CategoryNameDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CustomCategoryRepository {
    @Override
    @NonNull
    Optional<Category> findById(Long id);
    Optional<CategoryNameDTO> findCategoryById(Long id);
}

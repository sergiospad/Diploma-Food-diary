package org.kane.database.repository.recipe;

import org.kane.database.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, QuerydslPredicateExecutor<Recipe>, CustomRecipeRepository {

}

package org.kane.database.repository.recipe_recource.ingredient;

import org.kane.database.entity.recipe_recource.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>, CustomIngredientRepository{

}

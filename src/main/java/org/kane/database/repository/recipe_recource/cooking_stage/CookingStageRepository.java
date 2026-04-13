package org.kane.database.repository.recipe_recource.cooking_stage;

import org.kane.database.entity.recipe_recource.CookingStage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CookingStageRepository extends JpaRepository<CookingStage, Long>, CustomCookingStageRepository {
}

package org.kane.database.repository.recipe_recource.coefficient;

import org.kane.database.entity.recipe_recource.Coefficient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoefficientRepository extends JpaRepository<Coefficient, Long>, CustomCoefficientRepository {

}

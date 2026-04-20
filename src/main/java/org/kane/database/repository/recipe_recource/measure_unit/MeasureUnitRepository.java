package org.kane.database.repository.recipe_recource.measure_unit;

import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasureUnitRepository extends JpaRepository<MeasureUnit,Long>,  CustomMeasureUnitRepository {

}

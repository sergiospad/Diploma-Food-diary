package org.kane.database.repository.recipe_recource.measure_unit;

import org.kane.database.entity.recipe_recource.MeasureUnit;
import org.kane.domain.DTO.entityDTO.diary.recipe_recource.measure_unit.MeasureUnitDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasureUnitRepository extends JpaRepository<MeasureUnit,Long>,  CustomMeasureUnitRepository {
    Optional<MeasureUnitDTO> findAllById(Long id);

}

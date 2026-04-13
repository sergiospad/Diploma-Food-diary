package org.kane.database.repository.weight_record;

import org.kane.database.entity.diary.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long>, CustomWeightRecordRepository {
}

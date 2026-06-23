package org.kane.database.repository.diary.diary_record;

import org.kane.database.entity.diary.DiaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRecordRepository extends JpaRepository<DiaryRecord, Long>, CustomDiaryRecordRepository {

}

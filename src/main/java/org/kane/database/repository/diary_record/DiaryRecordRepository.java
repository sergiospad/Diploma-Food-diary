package org.kane.database.repository.diary_record;

import org.kane.database.entity.diary.DiaryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface DiaryRecordRepository extends JpaRepository<DiaryRecord, Long>, CustomDiaryRecordRepository {

}

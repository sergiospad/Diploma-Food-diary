package org.kane.domain.service.diary.diary_record;

import org.kane.domain.DTO.entityDTO.diary.daily_record.DiaryRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.CalorieConsumptionShowDTO;
import org.kane.domain.DTO.request.DiaryRecordRequest;

import java.security.Principal;
import java.time.LocalDate;

public interface DiaryRecordService {
    void createDiaryRecord(Principal principal, LocalDate localDate);

    DiaryRecordShowDTO getDiaryRecord(Principal principal, DiaryRecordRequest diaryRecordRequest);

    CalorieConsumptionShowDTO getConsumptionOfDiaryRecord(Long userID, LocalDate date);
}

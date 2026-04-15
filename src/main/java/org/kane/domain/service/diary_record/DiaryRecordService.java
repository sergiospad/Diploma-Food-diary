package org.kane.domain.service.diary_record;

import org.kane.domain.DTO.entityDTO.daily_record.DiaryRecordShowDTO;
import org.kane.domain.DTO.entityDTO.sport_activity.CalorieConsumptionShowDTO;
import org.kane.domain.DTO.request.DiaryRecordRequest;

import java.security.Principal;
import java.time.LocalDate;

public interface DiaryRecordService {
    DiaryRecordShowDTO getDiaryRecord(Principal principal, DiaryRecordRequest diaryRecordRequest);

    CalorieConsumptionShowDTO getConsumptionOfDiaryRecord(Long userID, LocalDate date);
}

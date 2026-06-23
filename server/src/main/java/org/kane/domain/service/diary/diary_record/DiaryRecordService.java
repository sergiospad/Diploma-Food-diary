package org.kane.domain.service.diary.diary_record;

import org.kane.domain.DTO.entityDTO.diary.daily_record.DiaryRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.CalorieConsumptionShowDTO;
import org.kane.domain.DTO.request.DiaryRecordRequest;

import java.security.Principal;
import java.time.LocalDate;

public interface DiaryRecordService {
    void createDiaryRecord(Principal principal,  DiaryRecordRequest diaryRecordRequest);

    DiaryRecordShowDTO showDiaryRecord(Principal principal, DiaryRecordRequest diaryRecordRequest);

    CalorieConsumptionShowDTO getConsumptionOfDiaryRecord(Principal principal, LocalDate date);
}

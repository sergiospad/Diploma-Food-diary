package org.kane.web.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.diary.daily_record.DiaryRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.CalorieConsumptionShowDTO;
import org.kane.domain.DTO.request.DiaryRecordRequest;
import org.kane.domain.DTO.response.MessageResponse;
import org.kane.domain.service.diary.diary_record.DiaryRecordService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/diary")
@RequiredArgsConstructor
public class DiaryRecordController {

    private final DiaryRecordService diaryRecordService;

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<MessageResponse> createDiaryRecord(Principal principal,
                                                             @RequestBody DiaryRecordRequest diaryRecordRequest) {
        diaryRecordService.createDiaryRecord(principal, diaryRecordRequest);
        return ResponseEntity.ok().body(new MessageResponse("Diary Record Created Successfully"));
    }

    @PostMapping("/show")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<DiaryRecordShowDTO> showDiaryRecord(Principal principal,
                                                              @RequestBody DiaryRecordRequest diaryRecordRequest) {
        var result = diaryRecordService.showDiaryRecord(principal, diaryRecordRequest);
        return ResponseEntity.ok().body(result);
    }

    /**
     * GET /api/diary/consumption?date=2024-01-15T14:30:00
     */
    @GetMapping("/consumption")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CalorieConsumptionShowDTO> getConsumptionOfDiaryRecord(Principal principal,
                                                                                 @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        var result = diaryRecordService.getConsumptionOfDiaryRecord(principal, date);
        return ResponseEntity.ok().body(result);
    }
}

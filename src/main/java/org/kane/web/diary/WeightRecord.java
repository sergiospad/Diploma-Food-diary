package org.kane.web.diary;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.domain.DTO.entityDTO.diary.weight_record.CurrentWeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightChartDataDTO;
import org.kane.domain.DTO.request.WeightChartRequest;
import org.kane.domain.service.diary.weight_record.WeightRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/weightRecord")
@RequiredArgsConstructor
public class WeightRecord {

    private final WeightRecordService weightRecordService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<WeightRecordShowDTO>> getAllWeightRecords(Principal principal) {
        var result = weightRecordService.getAllRecords(principal);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CurrentWeightRecordShowDTO> getCurrentWeightRecord(Principal principal) {
        var result = weightRecordService.getCurrentRecord(principal);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CurrentWeightRecordShowDTO> createRecord(Principal principal,
                                                                   @RequestBody WeightRecordCreateDTO weightRecordCreateDTO){
        var result = weightRecordService.createRecord(principal, weightRecordCreateDTO);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/chart")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<WeightChartDataDTO> createChart(Principal principal,
                                                          @RequestBody WeightChartRequest weightChartRequest){
        var result = weightRecordService.formChart(principal, weightChartRequest);
        return ResponseEntity.ok().body(result);
    }
}

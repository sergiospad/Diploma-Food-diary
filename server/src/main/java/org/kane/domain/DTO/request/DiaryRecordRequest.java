package org.kane.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DiaryRecordRequest {
    LocalDate recordDate;
}

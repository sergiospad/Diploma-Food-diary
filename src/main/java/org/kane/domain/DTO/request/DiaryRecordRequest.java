package org.kane.domain.DTO.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DiaryRecordRequest {
    LocalDate recordDate;
}

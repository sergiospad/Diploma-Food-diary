package org.kane.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kane.database.enum_types.TaskStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ChangeStatusTaskRequest {
    Long id;
    TaskStatus status;
    LocalDate endingDate;
}

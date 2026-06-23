package org.kane.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.TaskStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeStatusTaskRequest {
    Long id;
    TaskStatus status;
    LocalDate endingDate;
}

package org.kane.domain.DTO.entityDTO.diary.daily_record;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutocalcAndIDProjection {
    private Long id;
    private Boolean autocalc;
}

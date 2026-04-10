package org.kane.domain.DTO.entityDTO;

import lombok.Builder;
import lombok.Data;
import org.kane.database.enum_types.Priority;

@Data
@Builder
public class TagDTO {
    private Long id;
    private String name;
    private Priority priority;
}

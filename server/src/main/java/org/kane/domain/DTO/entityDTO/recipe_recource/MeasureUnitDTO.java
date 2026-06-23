package org.kane.domain.DTO.entityDTO.recipe_recource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasureUnitDTO {
    private Long id;
    private String name;
}

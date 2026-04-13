package org.kane.domain.DTO.entityDTO.recipe_recource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {
    private Long id;
    private String name;
}

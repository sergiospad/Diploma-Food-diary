package org.kane.domain.DTO.entityDTO.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipePreviewDTO {
    private Long id;
    private String name;
    private String summary;
    private Long imageID;
}

package org.kane.domain.DTO.entityDTO.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

@AllArgsConstructor
@Data
@ProjectionConstructor
public class RecipeSummarySearchDTO {
    Long id;
    String name;
    String summary;
}

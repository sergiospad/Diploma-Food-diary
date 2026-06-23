package org.kane.domain.DTO.entityDTO.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ProjectionConstructor;

@Data
@ProjectionConstructor
@AllArgsConstructor
public class ProductSearchDTO {
    private Long id;
    private String name;
}

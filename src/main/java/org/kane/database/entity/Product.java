package org.kane.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.kane.database.entity.recipe_recource.Category;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue("PRODUCT")
@Indexed
public class Product extends NutritionalInfo{
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

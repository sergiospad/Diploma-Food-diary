package org.kane.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.kane.database.entity.recipe_recource.Category;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DiscriminatorValue("PRODUCT")
public class Product extends NutritionalInfo{
    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

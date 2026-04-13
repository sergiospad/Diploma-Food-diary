package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coefficient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double conversionFactor;

    @ManyToOne
    @JoinColumn(name = "measure_unit_id")
    private MeasureUnit measureUnit;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

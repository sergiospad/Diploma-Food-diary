package org.kane.database.entity.diary;

import jakarta.persistence.*;
import lombok.*;
import org.kane.database.converter.ProductWeightConverter;
import org.kane.database.entity.NutritionalInfo;
import org.kane.database.entity.physical_quantity.ProductWeight;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of={"id", "productWeight", "nutritionalInfo"})
public class MealItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_g")
    @Convert(converter = ProductWeightConverter.class)
    private ProductWeight productWeight;

    @OneToOne
    @JoinColumn(name = "nutrition_id")
    private NutritionalInfo nutritionalInfo;

    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
}

package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.converter.ProductWeightConverter;
import org.kane.database.entity.Product;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.physical_quantity.ProductWeight;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "spec_measure_unit_id")
    private MeasureUnit specMeasureUnit;

    @Column(name = "weight_g")
    @Convert(converter = ProductWeightConverter.class)
    private ProductWeight weight;

    @OneToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.recipe.getIngredients().add(this);
    }
}

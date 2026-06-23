package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.*;
import org.kane.database.converter.PathConverter;
import org.kane.database.entity.Recipe;

import java.nio.file.Path;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of={"id", "stageNumber", "description"})
public class CookingStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Short stageNumber;

    @OneToOne
    @JoinColumn(name = "image_id")
    private ImageModel image;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public void addRecipe(Recipe recipe) {
        this.recipe = recipe;
        this.recipe.getCookingStages().add(this);
    }
}

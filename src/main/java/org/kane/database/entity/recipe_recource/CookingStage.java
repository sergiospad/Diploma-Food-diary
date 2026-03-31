package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.converter.PathConverter;
import org.kane.database.entity.Recipe;

import java.nio.file.Path;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CookingStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Short stageNumber;

    @Column(name = "image_URL")
    @Convert(converter = PathConverter.class)
    private Path imageURL;

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

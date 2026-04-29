package org.kane.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.kane.database.converter.PathConverter;
import org.kane.database.entity.recipe_recource.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DiscriminatorValue("RECIPE")
@PrimaryKeyJoinColumn(name="id")
@Indexed
@ToString(callSuper = true, of = {"summary", "createdAt", "cookingTime"})
public class Recipe extends NutritionalInfo {
    @Column
    @FullTextField
    private String summary;

    @OneToOne
    @JoinColumn(name = "illustration_id")
    private ImageModel illustration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "tag_recipes",
            joinColumns = @JoinColumn(name = "recipe_ID"),
            inverseJoinColumns = @JoinColumn(name = "tag_ID")
    )
    @Builder.Default
    @Size(max = 15)
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    @Builder.Default
    @Size(max = 15)
    private List<CookingStage>  cookingStages = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    @Builder.Default
    @Size(max = 20)
    private List<Ingredient> ingredients = new ArrayList<>();

    @Column
    private Short cookingTime;

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    public void addCookingStage(CookingStage cookingStage) {
        this.cookingStages.add(cookingStage);
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

}

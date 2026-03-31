package org.kane.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.kane.database.converter.PathConverter;
import org.kane.database.entity.recipe_recource.Comment;
import org.kane.database.entity.recipe_recource.CookingStage;
import org.kane.database.entity.recipe_recource.Ingredient;
import org.kane.database.entity.recipe_recource.Tag;

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
public class Recipe extends NutritionalInfo {
    @Column
    private String summary;

    @Column(name = "illustration_URL")
    @Convert(converter = PathConverter.class)
    private Path illustrationURL;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tag_recipes",
            joinColumns = @JoinColumn(name = "recipe_ID"),
            inverseJoinColumns = @JoinColumn(name = "tag_ID")
    )
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    @Builder.Default
    private List<CookingStage>  cookingStages = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "recipe"
    )
    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

}

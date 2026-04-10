package org.kane.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.kane.database.converter.PathConverter;
import org.kane.database.entity.recipe_recource.*;

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

    @OneToOne
    @JoinColumn(name = "illustration_id")
    private ImageModel illustration;

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

}

package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.*;
import org.kane.database.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of= {"id", "name"})
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private List<Recipe> recipes =  new ArrayList<>();

}

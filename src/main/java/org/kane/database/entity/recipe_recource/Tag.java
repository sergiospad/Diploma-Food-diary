package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.entity.Recipe;
import org.kane.database.enum_types.Priority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private List<Recipe> recipes =  new ArrayList<>();

}

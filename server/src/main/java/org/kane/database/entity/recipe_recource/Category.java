package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.*;
import org.kane.database.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(of={"id", "name"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Coefficient> coefficients;

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "category"
    )
    @Builder.Default
    private List<Product> products = new ArrayList<>();

}

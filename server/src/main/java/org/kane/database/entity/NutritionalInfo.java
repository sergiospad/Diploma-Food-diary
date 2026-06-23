package org.kane.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.kane.database.converter.nutrients_converter.CaloriesConverter;
import org.kane.database.converter.nutrients_converter.CarbsConverter;
import org.kane.database.converter.nutrients_converter.FatConverter;
import org.kane.database.converter.nutrients_converter.ProteinConverter;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
@ToString(of={"id", "name", "calories", "fat", "protein", "carbs", "isPrivate", "discriminator"})
public abstract class NutritionalInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    protected User author;

    @Column
    @FullTextField
    protected String name;

    @Column(name = "calories_per_100g")
    @Convert(converter = CaloriesConverter.class)
    protected Calories calories;

    @Column(name = "protein_per_100g")
    @Convert(converter = ProteinConverter.class)
    protected Protein protein;

    @Column(name = "fat_per_100g")
    @Convert(converter = FatConverter.class)
    protected Fat fat;

    @Column(name = "carbs_per_100g")
    @Convert(converter = CarbsConverter.class)
    protected Carbs carbs;

    @Column(name = "private")
    protected Boolean isPrivate;

    @Column(name="discriminator", insertable = false, updatable = false)
    protected String discriminator;

}

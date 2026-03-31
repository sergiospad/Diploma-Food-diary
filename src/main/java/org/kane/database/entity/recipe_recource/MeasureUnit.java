package org.kane.database.entity.recipe_recource;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.MeasureType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasureUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private MeasureType name;
}

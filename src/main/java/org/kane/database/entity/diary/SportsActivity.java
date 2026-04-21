package org.kane.database.entity.diary;

import jakarta.persistence.*;
import lombok.*;
import org.kane.database.converter.HumanWeightConverter;
import org.kane.database.converter.nutrients_converter.CaloriesConverter;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sports_activity")
@ToString(of={"id", "name", "burnedCalories"})
public class SportsActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Convert(converter = CaloriesConverter.class)
    private Calories burnedCalories;

    @ManyToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "diary_record_id")
    private DiaryRecord diaryRecord;
}

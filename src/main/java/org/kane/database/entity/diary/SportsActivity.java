package org.kane.database.entity.diary;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.converter.HumanWeightConverter;
import org.kane.database.converter.nutrients_converter.CaloriesConverter;
import org.kane.database.entity.physical_quantity.nutrients.Calories;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "sports_activity")
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

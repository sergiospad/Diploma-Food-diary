package org.kane.database.entity.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.converter.nutrients_converter.CaloriesConverter;
import org.kane.database.converter.HumanWeightConverter;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.enum_types.TaskStatus;
import org.kane.database.enum_types.TaskTarget;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column
    private LocalDate beginningDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column
    private LocalDate endingDate;

    @Column
    @Convert(converter = CaloriesConverter.class)
    private Calories caloriesDeficit;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskTarget target;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name="target_weight_kg")
    @Convert(converter = HumanWeightConverter.class)
    private HumanWeight targetWeight;

}

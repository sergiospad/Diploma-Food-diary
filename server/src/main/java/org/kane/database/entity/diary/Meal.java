package org.kane.database.entity.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.kane.database.enum_types.MealType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(of={"id", "mealTime", "type"})
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "UTC")
    @Column(name = "mealtime")
    private LocalTime mealTime;

    @Column
    @Enumerated(EnumType.STRING)
    private MealType type;

    @OneToMany(
            cascade = { CascadeType.PERSIST, CascadeType.REFRESH },
            fetch = FetchType.LAZY,
            mappedBy = "meal"
    )
    @Builder.Default
    private List<MealItem> mealItems = new ArrayList<>();

    public void addMealItem(MealItem mealItem) {
        mealItems.add(mealItem);
        mealItem.setMeal(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_record_id")
    private DiaryRecord dailyRecord;

}

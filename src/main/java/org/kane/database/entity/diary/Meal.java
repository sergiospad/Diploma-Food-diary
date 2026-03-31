package org.kane.database.entity.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.MealType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "UTC")
    @Column(updatable = false)
    private LocalTime mealTime;

    @Column
    @Enumerated(EnumType.STRING)
    private MealType type;

    @OneToMany(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY,
            mappedBy = "meal"
    )
    @Builder.Default
    private List<MealItem> mealItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_record_id")
    private DiaryRecord dailyRecord;

    public void addDailyRecord(DiaryRecord dailyRecord) {
        this.dailyRecord = dailyRecord;
        this.dailyRecord.getMeals().add(this);
    }

}

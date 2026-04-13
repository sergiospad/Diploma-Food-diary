package org.kane.database.entity.diary;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.converter.HumanWeightConverter;
import org.kane.database.entity.User;
import org.kane.database.entity.physical_quantity.HumanWeight;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measured_weight_kg")
    @Convert(converter = HumanWeightConverter.class)
    private HumanWeight measuredWeight;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    @Column(name ="date_of_measurement")
    private LocalDate dateOfMeasurement;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

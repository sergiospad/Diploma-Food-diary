package org.kane.domain.DTO.entityDTO.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.enum_types.Gender;

@Data
@AllArgsConstructor
public class BMRInfoProjection {
    private HumanWeight weight;
    private Gender gender;
    private Short height;
    private Short age;
}

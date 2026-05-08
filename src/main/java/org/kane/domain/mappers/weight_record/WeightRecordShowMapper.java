package org.kane.domain.mappers.weight_record;

import org.kane.domain.DTO.entityDTO.diary.weight_record.CurrentWeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class WeightRecordShowMapper implements Mapper<WeightRecordShowDTO, CurrentWeightRecordShowDTO> {
    @Override
    public CurrentWeightRecordShowDTO map(WeightRecordShowDTO from) {
        var to = new CurrentWeightRecordShowDTO();
        if (from == null) return to;
        to.setId(from.getId());
        to.setMeasure(from.getMeasure());
        to.setDate(from.getDate());
        return to;
    }
}

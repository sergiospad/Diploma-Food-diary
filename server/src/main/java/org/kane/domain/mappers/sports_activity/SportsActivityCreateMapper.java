package org.kane.domain.mappers.sports_activity;

import org.kane.database.entity.diary.SportsActivity;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportsActivityCreateDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SportsActivityCreateMapper implements Mapper<SportsActivityCreateDTO, SportsActivity> {
    @Override
    public SportsActivity map(SportsActivityCreateDTO from) {
        return SportsActivity.builder()
                .name(from.getName())
                .burnedCalories(from.getCalories())
                .build();
    }
}

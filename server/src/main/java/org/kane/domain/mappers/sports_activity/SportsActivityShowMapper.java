package org.kane.domain.mappers.sports_activity;

import org.kane.database.entity.diary.SportsActivity;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityShowDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class SportsActivityShowMapper implements Mapper<SportsActivity, SportActivityShowDTO> {
    @Override
    public SportActivityShowDTO map(SportsActivity from) {
        return SportActivityShowDTO.builder()
                .id(from.getId())
                .name(from.getName())
                .calories(from.getBurnedCalories())
                .build();
    }
}

package org.kane.domain.mappers.sports_activity;

import org.kane.database.entity.diary.SportsActivity;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityEditDTO;
import org.kane.domain.mappers.CopyMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SportsActivityEditMapper implements CopyMapper<SportActivityEditDTO, SportsActivity> {

    @Override
    public SportsActivity copyMap(SportActivityEditDTO from, SportsActivity to) {
        to.setName(Optional.ofNullable(from.getName()).orElse(to.getName()));
        to.setBurnedCalories(Optional.ofNullable(from.getCalories()).orElse(to.getBurnedCalories()));
        return to;
    }
}

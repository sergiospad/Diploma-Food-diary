package org.kane.domain.service.diary.sports_activity;

import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityEditDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportsActivityCreateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

public interface SportsActivityService {

    SportActivityShowDTO createActivity(Principal principal, SportsActivityCreateDTO sportsActivityCreateDTO);

    @Transactional
    SportActivityShowDTO updateSportActivity(SportActivityEditDTO sportActivityEditDTO);

    @Transactional
    void deleteSportActivity(Long sportsActivityID);
}

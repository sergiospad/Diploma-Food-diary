package org.kane.domain.service.diary.sports_activity;

import lombok.RequiredArgsConstructor;
import org.kane.database.entity.diary.SportsActivity;
import org.kane.database.repository.diary.diary_record.DiaryRecordRepository;
import org.kane.database.repository.diary.sport_activities.SportActivitiesRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityEditDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityShowDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportsActivityCreateDTO;
import org.kane.domain.mappers.sports_activity.SportsActivityCreateMapper;
import org.kane.domain.mappers.sports_activity.SportsActivityEditMapper;
import org.kane.domain.mappers.sports_activity.SportsActivityShowMapper;
import org.kane.exceptions.not_found.SportsActivityNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class SportsActivityServiceImpl implements SportsActivityService {

    private final SportsActivityCreateMapper sportsActivityCreateMapper;
    private final DiaryRecordRepository diaryRecordRepository;
    private final UserRepository userRepository;
    private final SportActivitiesRepository sportActivitiesRepository;
    private final SportsActivityShowMapper sportsActivityShowMapper;
    private final SportsActivityEditMapper sportsActivityEditMapper;

    @Transactional
    @Override
    public SportActivityShowDTO createActivity(Principal principal, SportsActivityCreateDTO sportsActivityCreateDTO){
        SportsActivity sportsActivity = sportsActivityCreateMapper.map(sportsActivityCreateDTO);
        var userID = userRepository.getCurrentUserId(principal);
        sportsActivity.setDiaryRecord(diaryRecordRepository.getDiaryRecordByRecordDate(sportsActivityCreateDTO.getDiaryRecordDate(), userID));
        sportsActivity = sportActivitiesRepository.save(sportsActivity);
        return sportsActivityShowMapper.map(sportsActivity);
    }

    @Transactional
    @Override
    public SportActivityShowDTO updateSportActivity(SportActivityEditDTO sportActivityEditDTO){
        return sportActivitiesRepository.findById(sportActivityEditDTO.getId())
                .map(sa-> sportsActivityEditMapper.copyMap(sportActivityEditDTO, sa))
                .map(sportActivitiesRepository::save)
                .map(sportsActivityShowMapper::map)
                .orElseThrow(()-> new SportsActivityNotFound("SportsActivity not found"));

    }

    @Transactional
    @Override
    public void deleteSportActivity(Long sportsActivityID){
        sportActivitiesRepository.deleteById(sportsActivityID);
    }
}

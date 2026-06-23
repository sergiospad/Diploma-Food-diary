package org.kane.database.repository.diary.sport_activities;

import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityShowDTO;

import java.util.List;

public interface CustomSportsActivityRepository {

    List<SportActivityShowDTO> getActivities(Long diaryRecordID);
}

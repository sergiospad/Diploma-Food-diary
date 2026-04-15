package org.kane.database.repository.sport_activities;

import org.kane.domain.DTO.entityDTO.sport_activity.SportActivityShowDTO;

import java.util.List;

public interface CustomSportsActivityRepository {

    List<SportActivityShowDTO> getActivities(Long diaryRecordID);
}

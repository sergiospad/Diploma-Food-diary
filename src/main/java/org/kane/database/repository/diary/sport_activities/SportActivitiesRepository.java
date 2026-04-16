package org.kane.database.repository.diary.sport_activities;

import org.kane.database.entity.diary.SportsActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportActivitiesRepository extends JpaRepository<SportsActivity, Long>, CustomSportsActivityRepository {
}

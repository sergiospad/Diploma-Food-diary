package org.kane.database.repository.diary.sport_activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.diary.DiaryRecord;
import org.kane.database.entity.diary.SportsActivity;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityShowDTO;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/diary/data-sports-activity-test-repository.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class SportsActivityRepositoryTest extends IntegrationTestBase {

    private DiaryRecord savedDiaryRecord;
    @Autowired
    private SavedEntities savedEntities;
    List<SportsActivity> sportsActivities;
    @Autowired
    private SportActivitiesRepository sportActivitiesRepository;

    @BeforeEach
    void setUp() {
        sportsActivities = new ArrayList<>();
        savedDiaryRecord = savedEntities.getDiaryRecord();
        sportsActivities.add(SportsActivity.builder()
                .id(1L)
                .name("Бег")
                .burnedCalories(new Calories(350.0))
                .diaryRecord(savedDiaryRecord)
                .build());
        sportsActivities.add(SportsActivity.builder()
                .id(2L)
                .name("Ходьба")
                .burnedCalories(new Calories(150.0))
                .diaryRecord(savedDiaryRecord)
                .build());
    }

    @Test
    void getActivities() {
        var activities = sportActivitiesRepository.getActivities(savedDiaryRecord.getId());
        assertThat(activities).isNotEmpty().hasSize(2);
        var activitiesID = activities.stream().map(SportActivityShowDTO::getId).toList();
        assertThat(activitiesID).containsAll(sportsActivities.stream().map(SportsActivity::getId).toList());
        var activitiesName = activities.stream().map(SportActivityShowDTO::getName).toList();
        assertThat(activitiesName).containsAll(sportsActivities.stream().map(SportsActivity::getName).toList());
        var activitiesBurnedCalories = activities.stream().map(SportActivityShowDTO::getCalories).toList();
        assertThat(activitiesBurnedCalories).containsAll(sportsActivities.stream().map(SportsActivity::getBurnedCalories).toList());
    }
}
package org.kane.domain.service.diary.sports_activity;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.repository.diary.sport_activities.SportActivitiesRepository;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportActivityEditDTO;
import org.kane.domain.DTO.entityDTO.diary.sport_activity.SportsActivityCreateDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.security.Principal;
import java.time.LocalDate;

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
class SportsActivityServiceImplTest extends IntegrationTestServiceBase {

    @Autowired
    private SportsActivityService sportsActivityService;
    @Autowired
    private SportActivitiesRepository sportActivitiesRepository;

    @Test
    void createActivity() {
        Principal principal = ()-> "chef_mike";
        var sacdto = SportsActivityCreateDTO.builder()
                .name("Пробежка")
                .calories(new Calories(500.0))
                .diaryRecordDate(LocalDate.of(2024, 1, 17))
                .build();
        var activities = sportsActivityService.createActivity(principal, sacdto);
        assertThat(activities).isNotNull();
        assertThat(activities.getId()).isEqualTo(6L);
        assertThat(activities.getName()).isEqualTo(sacdto.getName());
        assertThat(activities.getCalories()).isEqualTo(sacdto.getCalories());

    }

    @Test
    void updateSportActivity() {
        var sportsActivity = SportActivityEditDTO.builder()
                .id(5L)
                .name("Пробежка")
                .calories(new  Calories(500.0))
                .build();
        var sasdto = sportsActivityService.updateSportActivity(sportsActivity);
        assertThat(sasdto).isNotNull();
        assertThat(sasdto.getId()).isEqualTo(sportsActivity.getId());
        assertThat(sasdto.getName()).isEqualTo(sportsActivity.getName());
        assertThat(sasdto.getCalories()).isEqualTo(sportsActivity.getCalories());
    }

    @Test
    void deleteSportActivity() {
        var sa = sportActivitiesRepository.findById(1L).orElse(null);
        assertThat(sa).isNotNull();
        sportsActivityService.deleteSportActivity(1L);
        sa = sportActivitiesRepository.findById(1L).orElse(null);
        assertThat(sa).isNull();
    }
}
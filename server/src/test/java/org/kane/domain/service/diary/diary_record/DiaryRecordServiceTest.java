package org.kane.domain.service.diary.diary_record;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.physical_quantity.nutrients.Calories;
import org.kane.database.entity.physical_quantity.nutrients.Carbs;
import org.kane.database.entity.physical_quantity.nutrients.Fat;
import org.kane.database.entity.physical_quantity.nutrients.Protein;
import org.kane.database.repository.diary.diary_record.DiaryRecordRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.diary.meal_item.TotalMealShowDTO;
import org.kane.domain.DTO.request.DiaryRecordRequest;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/diary/data-diary-record-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class DiaryRecordServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private DiaryRecordService diaryRecordService;
    @Autowired
    private DiaryRecordRepository diaryRecordRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void getDiaryRecord() {
        var date = LocalDate.now();
        Principal principal = () -> "chef_mike";
        var record = diaryRecordService.showDiaryRecord(principal, new DiaryRecordRequest(date));
        assertThat(record.getDate()).isEqualTo(date);
        assertThat(record.getTotal()).isEqualTo(new TotalMealShowDTO());
        assertThat(record.getMeals()).isEmpty();
        assertThat(record.getCalorieConsumption().getAutoCalc()).isFalse();
        assertThat(record.getCalorieConsumption().getInRestConsumption().value).isBetween(1000.0, 2500.0);
        assertThat(record.getCalorieConsumption().getSportActivityShowDTOList()).isEmpty();
    }

    @Test
    void getDiaryRecord1() {
        Principal principal = () -> "chef_mike";
        var date = LocalDate.of(2024,1,17);
        var record = diaryRecordService.showDiaryRecord(principal, new DiaryRecordRequest(date));
        assertThat(record.getDate()).isEqualTo(date);
        var total = new TotalMealShowDTO();
        total.setCalories(new Calories(68.0*2+2.5*45.0));
        total.setProteins(new Protein(2.5*2+2.5*4.5));
        total.setFat(new Fat(1.50*2+2.5*1.50));
        total.setCarbs(new Carbs(12.00*2+2.5*4.00));
        assertThat(record.getTotal().getCalories()).isEqualTo(total.getCalories());
        assertThat(record.getTotal().getProteins()).isEqualTo(total.getProteins());
        assertThat(record.getTotal().getFat()).isEqualTo(total.getFat());
        assertThat(record.getTotal().getCarbs()).isEqualTo(total.getCarbs());
        assertThat(record.getCalorieConsumption().getAutoCalc()).isFalse();
        assertThat(record.getCalorieConsumption().getInRestConsumption().value).isBetween(1000.0, 2500.0);
        assertThat(record.getCalorieConsumption().getSportActivityShowDTOList()).hasSize(1);
        var activity = record.getCalorieConsumption().getSportActivityShowDTOList().getFirst();
        assertThat(activity.getId()).isEqualTo(5L);
        assertThat(activity.getCalories().value).isEqualTo(120.00);
        assertThat(record.getMeals()).hasSize(2);

    }

    @Test
    void getConsumptionOfDiaryRecord() {
        Principal principal = () -> "chef_mike";
        var date = LocalDate.of(2024,1,17);
        var cons = diaryRecordService.getConsumptionOfDiaryRecord(principal, date);
        assertThat(cons.getAutoCalc()).isFalse();
        assertThat(cons.getInRestConsumption().value).isBetween(1000.0, 2500.0);
        assertThat(cons.getSportActivityShowDTOList()).hasSize(1);
        var activity = cons.getSportActivityShowDTOList().getFirst();
        assertThat(activity.getId()).isEqualTo(5L);
        assertThat(activity.getCalories().value).isEqualTo(120.00);
    }

    @Transactional
    @Test
    void createDiaryRecord(){
        Principal principal = () -> "lisa_cook";
        var userID = userRepository.getCurrentUserId(principal);
        var date = LocalDate.now();
        var dr = diaryRecordRepository.getDiaryRecordByRecordDate(date, userID);
        assertThat(dr).isNull();
        diaryRecordService.createDiaryRecord(principal, new DiaryRecordRequest(date));
        dr = diaryRecordRepository.getDiaryRecordByRecordDate(date, userID);
        assertThat(dr).isNotNull();
        assertThat(dr.getId()).isNotNull().isEqualTo(6L);
        assertThat(dr.getAutoCalculation()).isFalse();
        assertThat(dr.getRecordDate()).isEqualTo(date);
        assertThat(dr.getUser().getId()).isEqualTo(userID);
    }
}
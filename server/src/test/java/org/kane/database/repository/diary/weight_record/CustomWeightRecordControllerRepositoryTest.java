package org.kane.database.repository.diary.weight_record;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.User;
import org.kane.database.entity.diary.WeightRecord;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightPointDTO;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/diary/data-weight-record-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class CustomWeightRecordControllerRepositoryTest extends IntegrationTestBase {

    private User savedUser;
    private WeightRecord savedWeightRecord1;
    private WeightRecord savedWeightRecord2;
    private WeightRecord savedWeightRecord3;
    @Autowired
    private SavedEntities savedEntities;
    @Autowired
    private WeightRecordRepository weightRecordRepository;

    @BeforeEach
    void setUp() {
        savedUser = savedEntities.getUser();
        savedWeightRecord1 = WeightRecord.builder()
                .id(1L)
                .measuredWeight(new HumanWeight(85.5))
                .dateOfMeasurement(LocalDate.of(2024, 1, 1))
                .user(savedUser)
                .build();
        savedWeightRecord2 = WeightRecord.builder()
                .id(2L)
                .measuredWeight(new HumanWeight(84.2))
                .dateOfMeasurement(LocalDate.of(2024, 1, 6))
                .user(savedUser)
                .build();
        savedWeightRecord3 = WeightRecord.builder()
                .id(3L)
                .measuredWeight(new HumanWeight(83.0))
                .dateOfMeasurement(LocalDate.of(2024, 2, 15))
                .user(savedUser)
                .build();
    }

    @Test
    void findAllRecordsByUserID() {
        var records = weightRecordRepository.findAllRecordsByUserID(savedUser.getId());
        assertThat(records).isNotEmpty().hasSize(3);
        var expectedRecords = List.of(savedWeightRecord1, savedWeightRecord2, savedWeightRecord3);
        var recordIDs = records.stream().map(WeightRecordShowDTO::getId).toList();
        assertThat(recordIDs).containsAll(expectedRecords.stream().map(WeightRecord::getId).toList());
        var recordWeights = records.stream().map(WeightRecordShowDTO::getMeasure).toList();
        assertThat(recordWeights).containsAll(expectedRecords.stream().map(WeightRecord::getMeasuredWeight).toList());
        var recordDates = records.stream().map(WeightRecordShowDTO::getDate).toList();
        assertThat(recordDates).containsAll(expectedRecords.stream().map(WeightRecord::getDateOfMeasurement).toList());
    }

    @Test
    void findLastRecordOfUser() {
        var record = weightRecordRepository.findLastRecordOfUser(savedUser.getId());
        assertThat(record).isNotNull();
        assertThat(record.getId()).isEqualTo(savedWeightRecord3.getId());
        assertThat(record.getMeasure()).isEqualTo(savedWeightRecord3.getMeasuredWeight());
        assertThat(record.getDate()).isEqualTo(savedWeightRecord3.getDateOfMeasurement());
    }

    @Test
    void getWeightByDay() {
        var record = weightRecordRepository.getWeightByDay(savedUser.getId(), LocalDate.of(2024, 1,1), LocalDate.of(2024,1, 10));
        assertThat(record).isNotEmpty().hasSize(2);
        var expectedRecords = List.of(savedWeightRecord1, savedWeightRecord2);
        var recordDates = record.stream().map(WeightPointDTO::getDate).toList();
        assertThat(recordDates).containsAll(expectedRecords.stream().map(WeightRecord::getDateOfMeasurement).toList());
        var recordWeights= record.stream().map(WeightPointDTO::getWeight).toList();
        assertThat(recordWeights).containsAll(expectedRecords.stream().map(WeightRecord::getMeasuredWeight).toList());
    }

    @Test
    void getWeightByWeek() {
        var record = weightRecordRepository.getWeightByWeek(savedUser.getId(), LocalDate.of(2024, 1,1), LocalDate.of(2024,1, 14));
        assertThat(record).isNotEmpty().hasSize(1);
        var recordDates = record.stream().map(WeightPointDTO::getDate).toList();
        assertThat(recordDates).containsAll(List.of(LocalDate.of(2024, 1,1)));
        var recordWeights= record.stream().map(WeightPointDTO::getWeight).toList();
        var newWeight = new HumanWeight((savedWeightRecord1.getMeasuredWeight().value()+ savedWeightRecord2.getMeasuredWeight().value())/2);
        assertThat(recordWeights).containsAll(List.of(newWeight));

    }

    @Test
    void getWeightByMonth() {
        var record = weightRecordRepository.getWeightByMonth(savedUser.getId(), LocalDate.of(2024, 1,1), LocalDate.of(2024,2, 1));
        assertThat(record).isNotEmpty().hasSize(1);
        var recordDates = record.stream().map(WeightPointDTO::getDate).toList();
        assertThat(recordDates).containsAll(List.of(LocalDate.of(2024, 1,1)));
        var recordWeights= record.stream().map(WeightPointDTO::getWeight).toList();
        var newWeight = new HumanWeight((savedWeightRecord1.getMeasuredWeight().value()+ savedWeightRecord2.getMeasuredWeight().value())/2);
        assertThat(recordWeights).containsAll(List.of(newWeight));
    }

    @Test
    void getWeightByYear() {
        var record = weightRecordRepository.getWeightByYear(savedUser.getId(), LocalDate.of(2024, 1,1), LocalDate.of(2025,1, 1));
        assertThat(record).isNotEmpty().hasSize(1);
        var recordDates = record.stream().map(WeightPointDTO::getDate).toList();
        assertThat(recordDates).containsAll(List.of(LocalDate.of(2024, 1,1)));
        var recordWeights= record.stream().map(WeightPointDTO::getWeight).toList();
        var newWeight = new HumanWeight((savedWeightRecord1.getMeasuredWeight().value()+ savedWeightRecord2.getMeasuredWeight().value() + savedWeightRecord3.getMeasuredWeight().value())/3);
        assertThat(recordWeights).containsAll(List.of(newWeight));
    }


    @Test
    void hasAnyData() {
        var data = weightRecordRepository.hasAnyData(savedUser.getId(), LocalDate.of(2024, 1,1), LocalDate.of(2024,1, 14));
        assertThat(data).isTrue();
        data = weightRecordRepository.hasAnyData(savedUser.getId(), LocalDate.of(2024, 3,1), LocalDate.of(2024, 4, 1));
        assertThat(data).isFalse();
    }

    @Test
    void getLastMeasurement() {
        var record = weightRecordRepository.getLastMeasurement(savedUser.getId());
        assertThat(record).isNotNull();
        assertThat(record.getMeasuredWeight()).isEqualTo(savedWeightRecord3.getMeasuredWeight());
        assertThat(record.getId()).isEqualTo(savedWeightRecord3.getId());
        assertThat(record.getDateOfMeasurement()).isEqualTo(savedWeightRecord3.getDateOfMeasurement());
        assertThat(record.getUser().getId()).isEqualTo(savedUser.getId());
    }
}
package org.kane.domain.service.diary.weight_record;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.enum_types.ChartPeriodType;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordCreateDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.WeightRecordShowDTO;
import org.kane.domain.DTO.entityDTO.diary.weight_record.for_chart.WeightPointDTO;
import org.kane.domain.DTO.request.WeightChartRequest;
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
                scripts = "classpath:sql/diary/data-weight-record-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class WeightRecordControllerServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private WeightRecordService weightRecordService;

    @Test
    void getAllRecords() {
        Principal principal = () -> "john_doe";
        var records = weightRecordService.getAllRecords(principal);
        assertThat(records).hasSize(3);
        assertThat(records.stream().map(WeightRecordShowDTO::getId)).containsExactlyInAnyOrder(1L, 2L, 3L);
        assertThat(records.stream().map(WeightRecordShowDTO::getDate))
                .containsExactlyInAnyOrder(LocalDate.of(2024, 1,1), LocalDate.of(2024, 1, 6), LocalDate.of(2024, 2,15));
        assertThat(records.stream().map(WeightRecordShowDTO::getMeasure)
                .map(HumanWeight::value)).containsExactlyInAnyOrder(85.5, 84.2, 83.0);
    }

    @Test
    void getCurrentRecord() {
        Principal principal = () -> "john_doe";
        var record = weightRecordService.getCurrentRecord(principal);
        assertThat(record).isNotNull();
        assertThat(record.getId()).isEqualTo(3L);
        assertThat(record.getDate()).isEqualTo(LocalDate.of(2024, 2, 15));
        assertThat(record.getMeasure().value()).isEqualTo(83.0);
        assertThat(record.getBMI()).isBetween(18.0, 30.0);
    }

    @Test
    void getCurrentRecord1() {
        Principal principal = () -> "lisa_cook";
        var record = weightRecordService.getCurrentRecord(principal);
        assertThat(record).isNotNull();
        assertThat(record.getId()).isNull();
        assertThat(record.getDate()).isNull();
        assertThat(record.getMeasure()).isNull();
    }

    @Test
    void createRecord() {
        Principal principal = () -> "john_doe";
        var wrcdto = new WeightRecordCreateDTO(new HumanWeight(80.0), LocalDate.now());
        var record = weightRecordService.createRecord(principal, wrcdto);
        assertThat(record).isNotNull();
        assertThat(record.getId()).isEqualTo(10L);
        assertThat(record.getDate()).isEqualTo(LocalDate.now());
        assertThat(record.getMeasure()).isEqualTo(wrcdto.getMeasure());
        assertThat(record.getBMI()).isBetween(18.0, 30.0);
    }


    @Test
    void formChart() {
        Principal principal = () -> "john_doe";
        WeightChartRequest request = new WeightChartRequest();
        request.setChartPeriodType(ChartPeriodType.DAY);
        request.setStartDate(LocalDate.of(2024, 1, 1));
        request.setEndDate(LocalDate.of(2024, 2, 20));

        var chart = weightRecordService.formChart(principal, request);

        assertThat(chart).isNotNull();
        assertThat(chart.getPeriod()).isEqualTo(ChartPeriodType.DAY);
        assertThat(chart.getStartDate()).isEqualTo(request.getStartDate());
        assertThat(chart.getEndDate()).isEqualTo(request.getEndDate());
        assertThat(chart.getWeightList()).hasSize(3);
        assertThat(chart.getWeightList().stream().map(WeightPointDTO::getDate).toList())
                .containsExactly(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 6), LocalDate.of(2024, 2, 15));
        assertThat(chart.getWeightList().stream().map(p -> p.getWeight().value()).toList())
                .containsExactly(85.5, 84.2, 83.0);
        assertThat(chart.getMaxWeight().value()).isEqualTo(85.5);
        assertThat(chart.getMinWeight().value()).isEqualTo(83.0);
        assertThat(chart.getAvgWeight().value()).isEqualTo((85.5 + 84.2 + 83.0) / 3.0);
        assertThat(chart.getWeightChange().value()).isEqualTo(85.5 - 83.0);
    }
}
package org.kane.domain.service.diary.diary_record;

import org.junit.jupiter.api.Test;
import org.kane.database.repository.diary.diary_record.DiaryRecordRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;

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

    }

    @Test
    void getConsumptionOfDiaryRecord() {
    }

    @Transactional
    @Test
    void createDiaryRecord(){
        Principal principal = () -> "lisa_cook";
        var userID = userRepository.getCurrentUserId(principal);
        var date = LocalDate.now();
        var dr = diaryRecordRepository.getDiaryRecordByRecordDate(date, userID);
        assertThat(dr).isNull();
        diaryRecordService.createDiaryRecord(principal, date);
        dr = diaryRecordRepository.getDiaryRecordByRecordDate(date, userID);
        assertThat(dr).isNotNull();

    }
}
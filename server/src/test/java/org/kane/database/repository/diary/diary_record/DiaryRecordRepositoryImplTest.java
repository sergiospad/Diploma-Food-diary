package org.kane.database.repository.diary.diary_record;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.User;
import org.kane.database.entity.diary.DiaryRecord;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

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
class DiaryRecordRepositoryImplTest extends IntegrationTestBase {

    private DiaryRecord savedDiaryRecord;
    @Autowired
    private SavedEntities savedEntities;
    private User savedUser;
    @Autowired
    private DiaryRecordRepository diaryRecordRepository;

    @BeforeEach
    public void setUp() {
        savedDiaryRecord = savedEntities.getDiaryRecord();
        savedUser = savedEntities.getUser();
        savedDiaryRecord.setUser(savedUser);
    }

    @Test
    void getDiaryRecordByRecordDate() {
        var dr = diaryRecordRepository.getDiaryRecordByRecordDate(savedDiaryRecord.getRecordDate(), savedUser.getId());
        assertThat(dr).isNotNull();
        assertThat(dr.getRecordDate()).isEqualTo(savedDiaryRecord.getRecordDate());
        assertThat(dr.getId()).isEqualTo(savedDiaryRecord.getId());
        assertThat(dr.getUser().getId()).isEqualTo(savedUser.getId());
        assertThat(dr.getAutoCalculation()).isEqualTo(savedDiaryRecord.getAutoCalculation());
    }

    @Test
    void getIDAndAutocalc() {
        var dr = diaryRecordRepository.getIDAndAutocalc(savedDiaryRecord.getRecordDate(), savedUser.getId());
        assertThat(dr).isNotNull();
        assertThat(dr.getId()).isEqualTo(savedDiaryRecord.getId());
        assertThat(dr.getAutocalc()).isEqualTo(savedDiaryRecord.getAutoCalculation());
    }
}
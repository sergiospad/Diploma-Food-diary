package org.kane.domain.service.user;

import org.junit.jupiter.api.Test;
import org.kane.database.entity.User;
import org.kane.database.enum_types.ImageType;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.request.SignupRequest;
import org.kane.integration.IntegrationTestServiceBase;
import org.kane.integration.PostgresTestContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class UserCreateServiceTest extends IntegrationTestServiceBase {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Test
    void createUser_persistsUserWithDefaultAvatar() {
        SignupRequest req = new SignupRequest();
        req.setUsername("new_user");
        req.setEmail("new_user@mail.com");
        req.setPassword("password123");

        User created = userService.createUser(req);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getUsername()).isEqualTo("new_user");
        assertThat(created.getEmail()).isEqualTo("new_user@mail.com");
        assertThat(created.getAvatar()).isNotNull();
        assertThat(created.getAvatar().getImageType()).isEqualTo(ImageType.USER);
        User fromDb = userRepository.findById(created.getId()).orElseThrow();
        assertThat(fromDb.getUsername()).isEqualTo("new_user");
        assertThat(fromDb.getEmail()).isEqualTo("new_user@mail.com");
        assertThat(fromDb.getAvatar()).isNotNull();
    }

}
package org.kane.database.repository.user;

import org.junit.jupiter.api.Test;
import org.kane.config.QueryDSLConfig;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.enum_types.Gender;
import org.kane.database.enum_types.Role;
import org.kane.exceptions.not_found.UserNotFoundException;
import org.kane.integration.PostgresTestContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@ActiveProfiles("test")
@Sql({"classpath:sql/data-user-repository-test.sql"})
@Import(QueryDSLConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private static final PostgreSQLContainer<?> postgreSQLContainer = PostgresTestContainer.getInstance();

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    void findByID() {
        var user = userRepository.findById(1L)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("john_doe");
        assertThat(user.getPassword()).isEqualTo("$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy");
        assertTrue(bCryptPasswordEncoder.matches("password123", user.getPassword()));
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getHeight()).isEqualTo((short) 175);
        assertThat(user.getBirthdate()).isEqualTo(LocalDate.of(1990,5, 15));
        assertThat(user.getGender()).isEqualTo(Gender.M);
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 1, 10, 10, 0, 0));
        assertThat(user.getAvatar().getId()).isEqualTo(1L);
    }

    @Test
    void getBMRInfo(){
        var proj = userRepository.getBMRInfo(1L);
        assertThat(proj.getWeight()).isEqualTo(new HumanWeight(83.00));
        assertThat(proj.getGender()).isEqualTo(Gender.M);
        assertThat(proj.getHeight()).isEqualTo((short)175);
        LocalDate birthdate = LocalDate.of(1990, 5, 15);
        var age = ChronoUnit.YEARS.between(birthdate, LocalDate.now());
        assertThat(proj.getAge()).isEqualTo((short)age);

    }

    @Test
    void findByUsername() {
        var user = userRepository.findByUsername("john_doe")
                .orElseThrow(()->new UserNotFoundException("User not found"));
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("john_doe");
        assertThat(user.getPassword()).isEqualTo("$2a$10$z3SKjhcdAMmZ7CKfsHeoOOzJaEYk69UBhZq.BU/xYdCf7PPItvHOy");
        assertTrue(bCryptPasswordEncoder.matches("password123", user.getPassword()));
        assertThat(user.getEmail()).isEqualTo("john@example.com");
        assertThat(user.getBirthdate()).isEqualTo(LocalDate.of(1990,5, 15));
        assertThat(user.getGender()).isEqualTo(Gender.M);
        assertThat(user.getRole()).isEqualTo(Role.USER);
        assertThat(user.getCreatedAt()).isEqualTo(LocalDateTime.of(2024, 1, 10, 10, 0, 0));
        assertThat(user.getAvatar().getId()).isEqualTo(1L);

    }

    @Test
    void existsByUsername() {
        assertTrue(userRepository.existsByUsername("john_doe"));
        assertTrue(userRepository.existsByUsername("jane_smith"));
        assertFalse(userRepository.existsByUsername("test"));
    }

    @Test
    void existsByEmail() {
        assertTrue(userRepository.existsByEmail("john@example.com"));
        assertTrue(userRepository.existsByEmail("jane@example.com"));
        assertFalse(userRepository.existsByEmail("test"));
    }


}
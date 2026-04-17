package org.kane.database.repository.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kane.database.entity.User;
import org.kane.database.entity.physical_quantity.HumanWeight;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.exceptions.not_found.UserNotFoundException;
import org.kane.integration.IntegrationTestBase;
import org.kane.integration.SavedEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Sql({"classpath:sql/data-user-repository-test.sql"})
class UserRepositoryTest extends IntegrationTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SavedEntities savedEntities;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private User savedUser;
    private ImageModel im;

    @BeforeEach
    void setUp() {
        savedUser = savedEntities.getUser();
        im = savedEntities.getAvatar();
        savedUser.setAvatar(im);
    }

    @Test
    void findByID() {
        var user = userRepository.findById(1L)
                .orElseThrow(()->new UserNotFoundException("User not found"));
        assertThat(user.getId()).isEqualTo(savedUser.getId());
        assertThat(user.getUsername()).isEqualTo(savedUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(savedUser.getPassword());
        assertTrue(bCryptPasswordEncoder.matches("password123", user.getPassword()));
        assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(user.getHeight()).isEqualTo(savedUser.getHeight());
        assertThat(user.getBirthdate()).isEqualTo(savedUser.getBirthdate());
        assertThat(user.getGender()).isEqualTo(savedUser.getGender());
        assertThat(user.getRole()).isEqualTo(savedUser.getRole());
        assertThat(user.getCreatedAt()).isEqualTo(savedUser.getCreatedAt());
        assertThat(user.getAvatar().getId()).isEqualTo(savedUser.getAvatar().getId());
    }

    @Test
    void getBMRInfo(){
        var proj = userRepository.getBMRInfo(1L);
        //TODO заменить humanweight
        assertThat(proj.getWeight()).isEqualTo(new HumanWeight(83.00));
        assertThat(proj.getGender()).isEqualTo(savedUser.getGender());
        assertThat(proj.getHeight()).isEqualTo(savedUser.getHeight());
        var age = ChronoUnit.YEARS.between(savedUser.getBirthdate(), LocalDate.now());
        assertThat(proj.getAge()).isEqualTo((short)age);

    }

    @Test
    void findByUsername() {
        var user = userRepository.findByUsername(savedUser.getUsername())
                .orElseThrow(()->new UserNotFoundException("User not found"));
        assertThat(user.getId()).isEqualTo(savedUser.getId());
        assertThat(user.getUsername()).isEqualTo(savedUser.getUsername());
        assertThat(user.getPassword()).isEqualTo(savedUser.getPassword());
        assertTrue(bCryptPasswordEncoder.matches("password123", user.getPassword()));
        assertThat(user.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(user.getBirthdate()).isEqualTo(savedUser.getBirthdate());
        assertThat(user.getGender()).isEqualTo(savedUser.getGender());
        assertThat(user.getRole()).isEqualTo(savedUser.getRole());
        assertThat(user.getCreatedAt()).isEqualTo(savedUser.getCreatedAt());
        assertThat(user.getAvatar().getId()).isEqualTo(savedUser.getAvatar().getId());

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
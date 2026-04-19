package org.kane.domain.service.user;

import org.junit.jupiter.api.Test;
import org.kane.database.enum_types.Gender;
import org.kane.database.enum_types.Role;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.user.ChangeRoleDTO;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.integration.IntegrationTestServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@SqlGroup({
        @Sql(
                scripts = "classpath:sql/cleanup-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        ),
        @Sql(
                scripts = "classpath:sql/user/data-user-repository-test.sql",
                config = @SqlConfig(transactionMode = SqlConfig.TransactionMode.ISOLATED)
        )
})
class UserServiceTest extends IntegrationTestServiceBase {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void updateCurrentUser1() {
        var user = userRepository.findById(1L).orElseThrow();
        assertThat(user).isNotNull();
        var userEditDTO = UserEditDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .gender(Gender.FM)
                .height((short)200)
                .build();
        var savedUser = userService.updateUser(userEditDTO);
        assertThat(savedUser).as("Update assert").isTrue();
        var updatedUser = userRepository.findById(1L).orElseThrow();
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(updatedUser.getGender()).isEqualTo(userEditDTO.getGender());
        assertThat(updatedUser.getHeight()).isEqualTo(userEditDTO.getHeight());
    }

    @Test
    void changeRole() {
        var user = userRepository.findById(1L).orElseThrow();
        assertThat(user).isNotNull();
        assertThat(user.getRole()).isEqualTo(Role.USER);
        var changeRoleDTO = ChangeRoleDTO.builder().userId(1L).role(Role.ADMIN).build();
        userService.changeRole(changeRoleDTO);
        var updUser  = userRepository.findById(1L).orElseThrow();
        assertThat(updUser).isNotNull();
        assertThat(updUser.getRole()).isEqualTo(Role.ADMIN);
    }

}

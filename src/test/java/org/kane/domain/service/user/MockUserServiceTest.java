package org.kane.domain.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kane.database.entity.User;
import org.kane.database.enum_types.Role;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.user.ChangeRoleDTO;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.kane.domain.DTO.request.SignupRequest;
import org.kane.domain.DTO.request.UpdatePasswordRequest;
import org.kane.domain.mappers.user.SignupMapper;
import org.kane.domain.mappers.user.UserEditMapper;
import org.kane.database.enum_types.ImageType;
import org.kane.util.ImageUploadService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MockUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserEditMapper userEditMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private SignupMapper signupMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private Principal principal;

    @BeforeEach
    void setUp() {
        principal = () -> "test-user";
    }

    @Test
    void createUser_savesUserWithDefaultAvatar() {
        SignupRequest request = new SignupRequest();
        request.setUsername("alice");
        request.setEmail("alice@example.com");
        request.setPassword("secret");

        User mapped = User.builder()
                .username("alice")
                .email("alice@example.com")
                .password("encoded")
                .role(Role.USER)
                .build();

        when(signupMapper.map(request)).thenReturn(mapped);
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User saved = userService.createUser(request);

        assertThat(saved.getAvatar()).isNotNull();
        assertThat(saved.getAvatar().getImageType()).isEqualTo(ImageType.USER);
        assertThat(saved.getAvatar().getUrl()).isEqualTo(ImageUploadService.DEFAULT_USER_AVATAR_PATH);
        verify(userRepository).save(mapped);
    }

    @Test
    void updateCurrentUser_mapsAndSaves_returnsTrue() {
        UserEditDTO dto = UserEditDTO.builder().id(1L).username("bob").build();
        User entity = User.builder().id(1L).username("bob").email("test@mail.com").build();
        when(userRepository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(userEditMapper.copyMap( dto, entity)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(entity).then(invocation -> invocation.getArgument(0));
        assertThat(userService.updateUser(dto)).isTrue();
        verify(userRepository).save(entity);
    }

    @Test
    void updatePassword_whenOldMatches_encodesAndSaves() {
        User user = User.builder()
                .id(1L)
                .password("stored-hash")
                .build();
        UpdatePasswordRequest req = new UpdatePasswordRequest();
        req.setOldPassword("old-plain");
        req.setNewPassword("new-plain");

        when(userRepository.getCurrentUser(principal)).thenReturn(user);
        when(bCryptPasswordEncoder.matches("old-plain", "stored-hash")).thenReturn(true);
        when(bCryptPasswordEncoder.encode("new-plain")).thenReturn("new-hash");

        assertThat(userService.updatePassword(principal, req)).isTrue();
        assertThat(user.getPassword()).isEqualTo("new-hash");
        verify(bCryptPasswordEncoder).encode("new-plain");
        verify(userRepository).save(user);
    }

    @Test
    void updatePassword_whenOldDoesNotMatch_returnsFalseWithoutSave() {
        User user = User.builder().id(1L).password("stored-hash").build();
        UpdatePasswordRequest req = new UpdatePasswordRequest();
        req.setOldPassword("wrong");
        req.setNewPassword("new-plain");

        when(userRepository.getCurrentUser(principal)).thenReturn(user);
        when(bCryptPasswordEncoder.matches("wrong", "stored-hash")).thenReturn(false);

        assertThat(userService.updatePassword(principal, req)).isFalse();
        verify(bCryptPasswordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void changeRole_updatesRoleAndSaves() {
        User user = User.builder().id(10L).role(Role.USER).build();
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));

        userService.changeRole(ChangeRoleDTO.builder().userId(10L).role(Role.ADMIN).build());

        assertThat(user.getRole()).isEqualTo(Role.ADMIN);
        verify(userRepository).save(user);
    }

    @Test
    void getUserProfile_delegatesToRepository() {
        UserProfileDTO dto = UserProfileDTO.builder().id(1L).username("alice").avatarID(2L).build();
        when(userRepository.getCurrentUserProfile(principal)).thenReturn(dto);

        assertThat(userService.getUserProfile(principal)).isEqualTo(dto);
        verify(userRepository).getCurrentUserProfile(eq(principal));
    }

   @Test
   void updatePasswordTrue(){
        UpdatePasswordRequest req = UpdatePasswordRequest
                .builder()
                .oldPassword("123")
                .newPassword("1234")
                .build();
        var user = User.builder()
                .id(1L)
                .username("alice")
                .password(req.getOldPassword())
                .build();
        when(userRepository.getCurrentUser(principal)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(req.getOldPassword(), user.getPassword())).thenReturn(true);
        when(bCryptPasswordEncoder.encode(req.getNewPassword())).thenReturn(req.getNewPassword());
        when(userRepository.save(user)).thenReturn(user);
        assertThat(userService.updatePassword(principal, req)).isTrue();
        verify(userRepository).save(user);
        assertThat(user.getPassword()).isEqualTo(req.getNewPassword());
   }

   @Test
   void updatePasswordFalse(){
       UpdatePasswordRequest req = UpdatePasswordRequest
               .builder()
               .oldPassword("123")
               .newPassword("1234")
               .build();
       var user = User.builder()
               .id(1L)
               .username("alice")
               .password("0000")
               .build();
       when(userRepository.getCurrentUser(principal)).thenReturn(user);
       when(bCryptPasswordEncoder.matches(req.getOldPassword(), user.getPassword())).thenReturn(false);

       boolean result = userService.updatePassword(principal, req);
       assertThat(result).isFalse();

       verify(bCryptPasswordEncoder, never()).encode(any());

       verify(userRepository, never()).save(any());

       verify(bCryptPasswordEncoder, times(1)).matches(req.getOldPassword(), user.getPassword());

       assertThat(user.getPassword()).isEqualTo("0000");
   }

    @Test
    void getUserProfile(){
        when(userService.getUserProfile(principal)).thenReturn(new UserProfileDTO());

        var result = userService.getUserProfile(principal);

        assertThat(result).isEqualTo(new UserProfileDTO());

        verify(userRepository).getCurrentUserProfile(principal);
    }
}

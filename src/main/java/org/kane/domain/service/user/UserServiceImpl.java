package org.kane.domain.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.entity.User;
import org.kane.database.entity.recipe_recource.ImageModel;
import org.kane.database.enum_types.ImageType;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.user.ChangeRoleDTO;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.kane.domain.DTO.request.SignupRequest;
import org.kane.domain.DTO.request.UpdatePasswordRequest;
import org.kane.domain.mappers.user.SignupMapper;
import org.kane.domain.mappers.user.UserEditMapper;
import org.kane.exceptions.UserExistsException;
import org.kane.exceptions.not_found.UserNotFoundException;
import org.kane.util.ImageUploadService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserEditMapper userEditMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SignupMapper signupMapper;

    @Transactional
    @Override
    public User createUser(SignupRequest userIn) {

        var user = Optional.of(userIn)
                .map(signupMapper::map)
                .orElseThrow(()-> new UserExistsException("Ошибка при создании пользователя"));
        ImageModel im = ImageModel.builder()
                .imageType(ImageType.USER)
                .url(ImageUploadService.DEFAULT_USER_AVATAR_PATH)
                .build();
        user.setAvatar(im);
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public boolean updateUser(UserEditDTO userEditDTO) {
        var user = userRepository.findById(userEditDTO.getId())
                .orElseThrow(()->new UserNotFoundException("user not found"));
        try {
            Optional.of(userEditDTO)
                    .map(userEditDTO1 -> userEditMapper.copyMap(userEditDTO1, user))
                    .map(userRepository::save);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean updatePassword(Principal principal, UpdatePasswordRequest updatePasswordRequest) {
        var user = userRepository.getCurrentUser(principal);
       if(!bCryptPasswordEncoder.matches(updatePasswordRequest.getOldPassword(),
               user.getPassword()))
           return false;
       user.setPassword(bCryptPasswordEncoder.encode(updatePasswordRequest.getNewPassword()));
       userRepository.save(user);
       return true;
    }

    @Transactional
    @Override
    public void changeRole(ChangeRoleDTO changeRoleDTO) {
        var user = userRepository.findById(changeRoleDTO.getUserId())
                .orElseThrow(()->new UserNotFoundException("Пользователь не найден"));
        user.setRole(changeRoleDTO.getRole());
        userRepository.save(user);
    }

    @Override
    public UserProfileDTO getUserProfile(Principal principal) {
        return userRepository.getCurrentUserProfile(principal);
    }

    @Override
    public UserEditDTO getEditIndo(Principal principal){
        var userID = userRepository.getCurrentUserId(principal);
        return userRepository.getUserEditInfo(userID);
    }
}

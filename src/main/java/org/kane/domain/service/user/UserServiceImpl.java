package org.kane.domain.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.entity.User;
import org.kane.database.enum_types.Role;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.kane.domain.DTO.request.SignupRequest;
import org.kane.domain.DTO.request.UpdatePasswordRequest;
import org.kane.domain.mappers.SignupMapper;
import org.kane.domain.mappers.UserEditMapper;
import org.kane.exceptions.UserExistsException;
import org.kane.exceptions.UserNotFoundException;
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
    @Override
    public User createUser(SignupRequest userIn) {

        return Optional.of(userIn)
                .map(signupMapper::map)
                .map(userRepository::save)
                .orElseThrow(()-> new UserExistsException("Ошибка при создании пользователя"));
    }

    @Transactional
    @Override
    public boolean updateCurrentUser(Principal principal, UserEditDTO userEditDTO) {
        try {
            Optional.of(userEditDTO)
                    .map(userEditMapper::map)
                    .map(userRepository::save);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

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

    @Override
    public UserProfileDTO getUserProfile(Principal principal) {
        return userRepository.getCurrentUserProfile(principal);
    }
}

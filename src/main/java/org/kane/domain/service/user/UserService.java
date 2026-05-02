package org.kane.domain.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.entity.User;
import org.kane.domain.DTO.entityDTO.user.ChangeRoleDTO;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.kane.domain.DTO.request.SignupRequest;
import org.kane.domain.DTO.request.UpdatePasswordRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;


public interface UserService {
    User createUser(SignupRequest userIn);
    UserProfileDTO getUserProfile(Principal principal);
    boolean updateUser(UserEditDTO userEditDTO);
    boolean updatePassword(Principal principal, UpdatePasswordRequest updatePasswordRequest);
    void changeRole(ChangeRoleDTO changeRoleDTO);

    UserEditDTO getEditIndo(Principal principal);
}

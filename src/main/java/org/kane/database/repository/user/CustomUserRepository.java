package org.kane.database.repository.user;

import org.kane.database.entity.User;
import org.kane.domain.DTO.entityDTO.user.BMRInfoProjection;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;

import java.security.Principal;

public interface CustomUserRepository {

    User getCurrentUser(Principal principal);
    Long getCurrentUserId(Principal principal);
    UserProfileDTO getCurrentUserProfile(Principal principal);

    BMRInfoProjection getBMRInfo(Long userID);

    UserEditDTO getUserEditInfo(Long userID);
}

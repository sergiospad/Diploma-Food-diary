package org.kane.domain.mappers.user;

import org.kane.database.entity.User;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.mappers.CopyMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserEditMapper implements CopyMapper<UserEditDTO, User> {

    @Override
    public User copyMap(UserEditDTO from, User to) {
        to.setId(from.getId());
        to.setUsername(Optional.ofNullable(from.getUsername()).orElse(to.getUsername()));
        to.setEmail(Optional.ofNullable(from.getEmail()).orElse(to.getEmail()));
        to.setGender(Optional.ofNullable(from.getGender()).orElse(to.getGender()));
        to.setHeight(Optional.ofNullable(from.getHeight()).orElse(to.getHeight()));
        to.setBirthdate(Optional.ofNullable(from.getBirthDate()).orElse(to.getBirthdate()));
        return to;
    }
}

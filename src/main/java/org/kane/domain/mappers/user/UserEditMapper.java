package org.kane.domain.mappers.user;

import org.kane.database.entity.User;
import org.kane.domain.DTO.entityDTO.user.UserEditDTO;
import org.kane.domain.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserEditMapper implements Mapper<UserEditDTO, User> {
    @Override
    public User map(UserEditDTO from) {
        return User.builder()
                .id(from.getId())
                .username(from.getUsername())
                .email(from.getEmail())
                .gender(from.getGender())
                .height(from.getHeight())
                .birthdate(from.getBirthDate())
                .build();
    }
}

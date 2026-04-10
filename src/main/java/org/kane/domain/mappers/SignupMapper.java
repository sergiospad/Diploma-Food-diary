package org.kane.domain.mappers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.User;
import org.kane.database.enum_types.Role;
import org.kane.domain.DTO.request.SignupRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignupMapper implements Mapper<SignupRequest, User>{
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public User map(SignupRequest from) {
        return User.builder()
                .username(from.getUsername())
                .password(bCryptPasswordEncoder.encode(from.getPassword()))
                .email(from.getEmail())
                .role(Role.USER)
                .build();
    }
}

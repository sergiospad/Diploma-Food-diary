package org.kane.auth.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.kane.database.entity.User;
import org.kane.database.enum_types.Role;
import org.kane.database.repository.user.UserRepository;
import org.kane.exceptions.not_found.UserNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(@NonNull String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(Role.USER.name()), new SimpleGrantedAuthority(Role.ADMIN.name()));
        return new CustomUserDetails(username,user.getPassword(), authorities, user.getRole());
    }

}
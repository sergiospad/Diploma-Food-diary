package org.kane.auth.security;

import lombok.*;
import org.jspecify.annotations.Nullable;
import org.kane.database.enum_types.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@EqualsAndHashCode(callSuper = false)
public class CustomUserDetails extends User {
    @Getter
    private Role role;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Role role) {
        super(username, password, authorities);
        this.role = role;
    }
}

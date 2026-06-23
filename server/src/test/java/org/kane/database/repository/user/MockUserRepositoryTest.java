package org.kane.database.repository.user;

import com.querydsl.core.types.Projections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.kane.database.entity.User;
import org.kane.database.enum_types.Role;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

import static org.kane.database.entity.QUser.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MockUserRepositoryTest {

    @Mock
    private Principal principal;

    @Mock
    private JPAQueryFactory queryFactory;

    @Mock
    private JPAQuery<User> userQuery;

    @Mock
    private JPAQuery<Long> userIdQuery;

    @Mock
    private JPAQuery<UserProfileDTO> userProfileQuery;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    User savedUser;

    @BeforeEach
    void setUp() {
        savedUser = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john@example.com")
                .password("encoded")
                .role(Role.USER)
                .build();
    }

    @Test
    void getCurrentUser() {
        when(principal.getName()).thenReturn(savedUser.getUsername());
        when(queryFactory.select(user)).thenReturn(userQuery);
        when(userQuery.from(user)).thenReturn(userQuery);
        when(userQuery.where(any(Predicate.class))).thenReturn(userQuery);
        when(userQuery.fetchOne()).thenReturn(savedUser);

        var user = userRepository.getCurrentUser(principal);

        assertNotNull(user);
        assertEquals(savedUser.getId(), user.getId());
        assertEquals(savedUser.getUsername(), user.getUsername());
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertEquals(savedUser.getPassword(), user.getPassword());
        assertEquals(savedUser.getRole(), user.getRole());
    }

    @Test
    void getCurrentUserId() {

        when(principal.getName()).thenReturn(savedUser.getUsername());
        when(queryFactory.select(user.id)).thenReturn(userIdQuery);
        when(userIdQuery.from(user)).thenReturn(userIdQuery);
        when(userIdQuery.where(any(Predicate.class))).thenReturn(userIdQuery);
        when(userIdQuery.fetchOne()).thenReturn(savedUser.getId());

        var userID = userRepository.getCurrentUserId(principal);
        assertNotNull(userID);
        assertEquals(savedUser.getId(), userID);
    }

    @Test
    void getCurrentUserProfile() {
        var profile = UserProfileDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .avatarID(1L)
                .role(Role.USER)
                .build();
        when(principal.getName()).thenReturn(savedUser.getUsername());
        when(queryFactory.select(Projections.constructor(UserProfileDTO.class,
                user.id,
                user.username,
                user.avatar.id,
                user.role))).thenReturn(userProfileQuery);
        when(userProfileQuery.from(user)).thenReturn(userProfileQuery);
        when(userProfileQuery.where(any(Predicate.class))).thenReturn(userProfileQuery);
        when(userProfileQuery.fetchOne()).thenReturn(profile);

        UserProfileDTO result = userRepository.getCurrentUserProfile(principal);

        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getUsername(), result.getUsername());
        assertEquals(1L, result.getAvatarID());
        assertEquals(savedUser.getRole(), result.getRole());
    }
}

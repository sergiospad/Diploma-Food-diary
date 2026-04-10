package org.kane.database.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.User;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.springframework.stereotype.Repository;

import java.security.Principal;

import static org.kane.database.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements CustomUserRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public User getCurrentUser(Principal principal) {
        String username = principal.getName();
        return queryFactory.select(user)
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();
    }

    @Override
    public Long getCurrentUserId(Principal principal) {
        String username = principal.getName();
        return queryFactory.select(Projections.constructor(Long.class, user.id))
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();
    }

    @Override
    public UserProfileDTO getCurrentUserProfile(Principal principal) {
        String username = principal.getName();
        return queryFactory.select(Projections.constructor(UserProfileDTO.class,
                user.id,
                user.username,
                user.avatar.id))
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();
    }


}

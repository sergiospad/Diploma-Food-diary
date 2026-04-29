package org.kane.database.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kane.database.entity.User;
import org.kane.domain.DTO.entityDTO.user.BMRInfoProjection;
import org.kane.domain.DTO.entityDTO.user.UserProfileDTO;
import org.springframework.stereotype.Repository;

import java.security.Principal;

import static org.kane.database.entity.QUser.user;
import static org.kane.database.entity.diary.QWeightRecord.weightRecord;

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
        return queryFactory.select(user.id)
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
                user.avatar.id,
                user.role))
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();
    }

    @Override
    public BMRInfoProjection getBMRInfo(Long userID){
        NumberExpression<Short> yearsDiff = Expressions.numberTemplate(Short.class,
                "DATE_PART('year', AGE({0}))", user.birthdate);
        return queryFactory.select(Projections.constructor(BMRInfoProjection.class,
                    weightRecord.measuredWeight,
                    user.gender,
                    user.height,
                    yearsDiff
                )).from(user)
                .join(user.records, weightRecord)
                .where(user.id.eq(userID))
                .orderBy(weightRecord.dateOfMeasurement.desc())
                .fetchFirst();
    }


}

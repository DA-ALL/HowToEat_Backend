package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.QUser;
import com.daall.howtoeat.domain.user.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryQueryImpl implements UserRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<User> findUsersByConditions(String name, Boolean isNextGym, String orderBy, Pageable pageable) {
        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();

        OrderSpecifier<LocalDateTime> orderSpecifier = user.createdAt.desc();

        // USER, SUPERUSER 만 조회하도록
        builder.and(user.userRole.eq(UserRole.USER)).or(user.userRole.eq(UserRole.SUPERUSER));

        if (name != null && !name.isEmpty()) {
            builder.and(user.name.containsIgnoreCase(name));
        }

        if (isNextGym != null) {
            builder.and(user.isNextGym.eq(isNextGym));
        }

        if (orderBy != null && !orderBy.equals("desc")) {
            orderSpecifier = user.createdAt.asc();
        }

        List<User> content = jpaQueryFactory
                .selectFrom(user)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();

        Long total = jpaQueryFactory
                .select(user.count())
                .from(user)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

}

package com.daall.howtoeat.admin.ptmember;

import com.daall.howtoeat.domain.pt.PtMember;
import com.daall.howtoeat.domain.pt.QPtMember;
import com.daall.howtoeat.domain.user.QUser;
import com.daall.howtoeat.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PtMemberRepositoryQueryImpl implements PtMemberRepositoryQuery{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PtMember> findUsersByTrainerId(Long trainerId, Pageable pageable) {
        QPtMember ptMember = QPtMember.ptMember;
        QUser user = QUser.user;

        List<PtMember> content = jpaQueryFactory
                .selectFrom(ptMember)
                .join(ptMember.user, user).fetchJoin() // fetch join으로 n+1 방지
                .where(ptMember.trainer.id.eq(trainerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(ptMember.count())
                .from(ptMember)
                .where(ptMember.trainer.id.eq(trainerId))
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }

    @Override
    public Map<Long, Long> countPtMembersByTrainerIds(List<Long> trainerIds) {
        QPtMember ptMember = QPtMember.ptMember;

        return jpaQueryFactory
                .select(ptMember.trainer.id, ptMember.count())
                .from(ptMember)
                .where(ptMember.trainer.id.in(trainerIds))
                .groupBy(ptMember.trainer.id)
                .fetch()
                .stream()
                .collect(Collectors.toMap(
                        tuple -> tuple.get(ptMember.trainer.id),
                        tuple -> tuple.get(ptMember.count())
                ));
    }
}

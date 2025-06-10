package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.domain.pt.QTrainer;
import com.daall.howtoeat.domain.pt.Trainer;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.daall.howtoeat.domain.pt.QTrainer.trainer;


@Repository
@RequiredArgsConstructor
public class TrainerRepositoryQueryImpl implements TrainerRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Trainer> searchTrainers(String name, Long gymId, Pageable pageable) {

        List<Trainer> content = jpaQueryFactory.query()
                .select(trainer)
                .from(trainer)
                .where(
                        (name != null && !name.trim().isEmpty()) ? trainer.name.containsIgnoreCase(name.trim()) : null,
                        gymId != null ? trainer.gym.id.eq(gymId) : null
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(trainer.id.asc())
                .fetch();

        Long total = jpaQueryFactory
                .select(trainer.id.count())
                .from(trainer)
                .where(
                        (name != null && !name.trim().isEmpty()) ? trainer.name.containsIgnoreCase(name.trim()) : null,
                        gymId != null ? trainer.gym.id.eq(gymId) : null
                )
                .fetchOne();


        return new PageImpl<>(content, pageable, total);
    }
}

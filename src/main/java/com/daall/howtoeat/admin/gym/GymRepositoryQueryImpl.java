package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.admin.gym.dto.GymWithTrainerCountResponseDto;
import com.daall.howtoeat.domain.pt.QGym;
import com.daall.howtoeat.domain.pt.QTrainer;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GymRepositoryQueryImpl implements GymRepositoryQuery{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<GymWithTrainerCountResponseDto> getGymsWithTrainerCount(Pageable pageable, String name) {
        QGym gym = QGym.gym;
        QTrainer trainer = QTrainer.trainer;

        List<GymWithTrainerCountResponseDto> content = jpaQueryFactory
                .select(
                    Projections.constructor(
                        GymWithTrainerCountResponseDto.class,
                        gym.id,
                        gym.name,
                        trainer.count().intValue(),
                        gym.createdAt
                ))
                .from(gym)
                .leftJoin(trainer).on(trainer.gym.eq(gym))
                .where(name != null && !name.trim().isEmpty()
                        ? gym.name.containsIgnoreCase(name.trim())
                        : null)
                .groupBy(gym.id, gym.name)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(gym.id.countDistinct())
                .from(gym)
                .where(name != null && !name.trim().isEmpty()
                        ? gym.name.containsIgnoreCase(name.trim())
                        : null)
                .fetchOne();

        return new PageImpl<>(content, pageable, total != null ? total : 0);
    }
}

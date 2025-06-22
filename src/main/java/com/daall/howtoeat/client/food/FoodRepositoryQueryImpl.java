package com.daall.howtoeat.client.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.food.Food;
import com.daall.howtoeat.domain.food.QFood;
import com.daall.howtoeat.domain.food.QRecommendFood;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FoodRepositoryQueryImpl implements FoodRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AdminFoodResponseDto> searchFoods(int page, int size, String name, String orderBy, String foodType, String recommendation) {
        QFood food = QFood.food;
        QRecommendFood recommendFood = QRecommendFood.recommendFood;

        BooleanBuilder condition = new BooleanBuilder();

        // 필터 조건
        if (StringUtils.hasText(foodType) && !foodType.equals("all")) {
            condition.and(food.foodType.eq(FoodType.valueOf(foodType)));
        }

        // 추천 여부 조건
        if ("recommended".equals(recommendation)) {
            condition.and(recommendFood.id.isNotNull());
        }

        // name 필터
        if (name != null && !name.trim().isEmpty()) {
            condition.and(food.foodName.containsIgnoreCase(name));
        }


        // 정렬
        OrderSpecifier<LocalDateTime> orderSpecifier =
                "asc".equalsIgnoreCase(orderBy) ? food.createdAt.asc() : food.createdAt.desc();

        List<AdminFoodResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        AdminFoodResponseDto.class,
                        food.id,
                        food.foodName,
                        food.foodCode,
                        food.foodType,
                        food.representativeName,
                        food.kcal,
                        food.carbo,
                        food.protein,
                        food.fat,
                        food.foodWeight,
                        food.unit,
                        food.isPerServing,
                        recommendFood.id.isNotNull()
                ))
                .from(food)
                .leftJoin(recommendFood).on(recommendFood.food.eq(food))
                .where(condition)
                .orderBy(orderSpecifier)
                .offset((long) page * size)
                .limit(size)
                .fetch();

        // count 쿼리 (같은 조건 사용)
        Long total = jpaQueryFactory
                .select(food.count())
                .from(food)
                .leftJoin(recommendFood).on(recommendFood.food.eq(food))
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content, PageRequest.of(page, size), total != null ? total : 0);
    }

    @Override
    public Optional<AdminFoodResponseDto> findFoodAndRecommendationById(Long foodId){
        QFood food = QFood.food;
        QRecommendFood recommendFood = QRecommendFood.recommendFood;

        AdminFoodResponseDto result = jpaQueryFactory
                .select(Projections.constructor(
                        AdminFoodResponseDto.class,
                        food.id,
                        food.foodName,
                        food.foodCode,
                        food.foodType,
                        food.representativeName,
                        food.kcal,
                        food.carbo,
                        food.protein,
                        food.fat,
                        food.foodWeight,
                        food.unit,
                        food.isPerServing,
                        recommendFood.id.isNotNull()
                ))
                .from(food)
                .leftJoin(recommendFood).on(recommendFood.food.eq(food))
                .where(food.id.eq(foodId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}

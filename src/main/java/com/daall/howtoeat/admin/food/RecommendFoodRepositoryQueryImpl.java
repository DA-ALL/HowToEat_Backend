package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.domain.food.QFood;
import com.daall.howtoeat.domain.food.QRecommendFood;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecommendFoodRepositoryQueryImpl implements RecommendFoodRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AdminFoodResponseDto> searchRecommendFoodsAndFoods(String sortBy) {
        QRecommendFood recommendFood = QRecommendFood.recommendFood;
        QFood food = QFood.food;

        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortBy, food);

        return jpaQueryFactory
                .select(Projections.constructor(
                        AdminFoodResponseDto.class,
                        food.id,
                        food.foodName,
                        food.foodCode,
                        food.foodType,
                        food.representativeName,
                        food.providedBy,
                        food.kcal,
                        food.carbo,
                        food.protein,
                        food.fat,
                        food.foodWeight,
                        food.unit,
                        food.isPerServing,
                        recommendFood.id.isNotNull()
                ))
                .from(recommendFood)
                .leftJoin(food).on(recommendFood.food.eq(food))
                .orderBy(orderSpecifier)
                .fetch();
    }

    private OrderSpecifier<?> getOrderSpecifier(String sortBy, QFood food) {
        return switch (sortBy) {
            case "kcal" -> food.kcal.desc();
            case "protein" -> food.protein.desc();
            case "fat" -> food.fat.desc();
            case "carbo" -> food.carbo.desc();
            default -> food.kcal.desc();
        };
    }

}

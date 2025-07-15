package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.admin.favoritefood.dto.AdminFavoriteFoodWithUserResponseDto;
import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.favoritefood.QFavoriteFood;
import com.daall.howtoeat.domain.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoriteFoodRepositoryQueryImpl implements FavoriteFoodRepositoryQuery{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<AdminFavoriteFoodWithUserResponseDto> searchFavoriteFoods(int page, int size, String name, String orderBy, String adminShared) {
        QFavoriteFood favoriteFood = QFavoriteFood.favoriteFood;
        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();

        if (name != null && !name.isEmpty()) {
            builder.and(favoriteFood.foodName.containsIgnoreCase(name));
        }

        if (adminShared == null || adminShared.trim().isEmpty() || adminShared.equalsIgnoreCase("all")) {
            builder.and(
                    favoriteFood.foodType.eq(FoodType.CUSTOM_SHARED).or(favoriteFood.foodType.eq(FoodType.CUSTOM))
            );
        } else if(adminShared.equalsIgnoreCase("true")) {
            builder.and(favoriteFood.foodType.eq(FoodType.CUSTOM_SHARED));
        } else if (adminShared.equalsIgnoreCase("false")) {
            builder.and(favoriteFood.foodType.eq(FoodType.CUSTOM));
        }

        OrderSpecifier<LocalDateTime> orderSpecifier =
                "asc".equalsIgnoreCase(orderBy) ? favoriteFood.createdAt.asc() : favoriteFood.createdAt.desc();

        List<AdminFavoriteFoodWithUserResponseDto> content = jpaQueryFactory
                .select(Projections.constructor(
                        AdminFavoriteFoodWithUserResponseDto.class,
                        favoriteFood.id,
                        favoriteFood.foodName,
                        favoriteFood.foodCode,
                        favoriteFood.foodType,
                        favoriteFood.providedBy,
                        favoriteFood.kcal,
                        favoriteFood.carbo,
                        favoriteFood.protein,
                        favoriteFood.fat,
                        favoriteFood.foodWeight,
                        favoriteFood.unit,
                        favoriteFood.description,
                        favoriteFood.sharedAt,
                        user.id,
                        user.name,
                        user.profileImageUrl
                ))
                .from(favoriteFood)
                .leftJoin(favoriteFood.user, user)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset((long) page * size)
                .limit(size)
                .fetch();

        Long total = jpaQueryFactory
                .select(favoriteFood.count())
                .from(favoriteFood)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, PageRequest.of(page, size), total != null ? total : 0);
    }
}

package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumedFoodRepository extends JpaRepository<ConsumedFood, Long> {
    List<ConsumedFood> findAllByUserAndCreatedAtBetweenAndMealTime(User user, LocalDateTime start, LocalDateTime end, MealTime mealTime);
    Optional<List<ConsumedFood>> findAllByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    List<ConsumedFood> findAllByFavoriteFood(FavoriteFood favoriteFood);
    List<ConsumedFood> findByFavoriteFoodIn(List<FavoriteFood> favoriteFoods);

    //회원탈퇴 시 연관관계 끊기
//    void deleteAllByUser(User user);

    @Modifying
    @Query("DELETE FROM ConsumedFood c WHERE c.user = :user")
    void deleteAllByUser(@Param("user") User user);

    long countByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT cf.user.id, COUNT(cf) FROM ConsumedFood cf WHERE cf.user.id IN :userIds GROUP BY cf.user.id")
    List<Object[]> countConsumedFoodsByUserIds(@Param("userIds") List<Long> userIds);

    Long countByUser(User user);
}

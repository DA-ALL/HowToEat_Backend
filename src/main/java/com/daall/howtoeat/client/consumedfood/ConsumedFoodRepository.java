package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsumedFoodRepository extends JpaRepository<ConsumedFood, Long> {
    List<ConsumedFood> findAllByUserAndCreatedAtBetweenAndMealTime(User user, LocalDateTime start, LocalDateTime end, MealTime mealTime);
}

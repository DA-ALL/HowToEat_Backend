package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.domain.food.Food;
import com.daall.howtoeat.domain.food.RecommendFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecommendFoodRepository extends JpaRepository<RecommendFood, Long>, RecommendFoodRepositoryQuery {

    Optional<RecommendFood> findByFood(Food newFood);
}

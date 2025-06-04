package com.daall.howtoeat.client.food;

import com.daall.howtoeat.domain.food.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findByFoodNameContaining(String name, Pageable pageable);
}

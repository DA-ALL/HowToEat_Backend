package com.daall.howtoeat.client.food;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.food.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryQuery {
    Page<Food> findByFoodNameContaining(String name, Pageable pageable);
    Optional<Food> findById(Long id);

    @Query("SELECT MAX(CAST(SUBSTRING(f.foodCode, 6) AS int)) " +
            "FROM Food f WHERE f.foodCode LIKE 'Admin%'")
    Integer findMaxAdminFoodCodeNumber();

    Optional<Food> findByFoodCode(String foodCode);

    long countByFoodTypeIn(List<FoodType> ingredient);
}

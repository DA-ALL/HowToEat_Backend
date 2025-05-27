package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumedFoodRepository extends JpaRepository<ConsumedFood, Long> {
}

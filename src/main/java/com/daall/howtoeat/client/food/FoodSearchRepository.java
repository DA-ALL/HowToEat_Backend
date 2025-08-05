package com.daall.howtoeat.client.food;

import com.daall.howtoeat.domain.food.Food;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FoodSearchRepository extends ElasticsearchRepository<Food, Long> {

}

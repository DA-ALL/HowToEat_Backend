package com.daall.howtoeat.client.food.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FoodSearchRepository extends ElasticsearchRepository<FoodDocument, Long> {

}

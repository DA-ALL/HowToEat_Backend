package com.daall.howtoeat.client.food.elastic;

import com.daall.howtoeat.client.food.FoodRepository;
import com.daall.howtoeat.domain.food.Food;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ElasticsearchInitializer {
    private final ElasticsearchOperations operations;
    private final FoodRepository foodRepository;


    private static final int BATCH_SIZE = 5000;

    @PostConstruct
    public void init() {
        IndexOperations indexOps = operations.indexOps(FoodDocument.class);

        if (indexOps.exists()) {
            System.out.println("Deleting existing index...");
            indexOps.delete();
        }

        boolean created = indexOps.create();
        System.out.println("Index created? " + created);

        indexOps.putMapping(indexOps.createMapping());
        System.out.println("After putMapping");

        List<Food> allFoods = foodRepository.findAll();
        System.out.println("Indexing " + allFoods.size() + " foods...");

        for (int i = 0; i < allFoods.size(); i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, allFoods.size());
            List<Food> batch = allFoods.subList(i, end);

            List<IndexQuery> queries = batch.stream()
                    .map(food -> {
                        FoodDocument doc = new FoodDocument(food);
                        return new IndexQueryBuilder()
                                .withId(String.valueOf(food.getId())) // Long -> String
                                .withObject(doc)
                                .build();
                    })
                    .toList();


            // Bulk 색인
            operations.bulkIndex(queries, FoodDocument.class);
            System.out.println("Indexed batch " + i + " ~ " + (end - 1));
        }

        System.out.println("All foods indexed successfully!");
    }


}

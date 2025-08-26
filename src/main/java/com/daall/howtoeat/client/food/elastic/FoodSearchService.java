package com.daall.howtoeat.client.food.elastic;

import com.daall.howtoeat.client.food.dto.FoodResponseDto;
import com.daall.howtoeat.common.dto.ScrollResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodSearchService {
    private final ElasticsearchOperations operations;

    public ScrollResponseDto<FoodResponseDto> search(String keyword, int page, int size) throws IOException {
        String queryTemplate = new String(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream("elasticsearch/food_search.json")
                ).readAllBytes(),
                StandardCharsets.UTF_8
        );

        int from = page * size;

        String finalQuery = queryTemplate
                .replace("{{keyword}}", keyword);

        Query query = new StringQuery(finalQuery);
        query.setPageable(PageRequest.of(page, size));

        System.out.println("finalQuery: " + finalQuery);
        System.out.println("query: " + query);
        try {
            SearchHits<FoodDocument> searchHits = operations.search(query, FoodDocument.class);

            List<FoodResponseDto> pageContent = searchHits.getSearchHits().stream()
                    .map(SearchHit::getContent)
                    .map(FoodResponseDto::new)
                    .toList();

            long totalHits = searchHits.getTotalHits(); // 전체 결과 개수
            boolean hasNext = (from + size) < totalHits;

            System.out.println("ESearch Result: " + pageContent);

            return new ScrollResponseDto<>(pageContent, hasNext);
        } catch (Exception e) {
            System.out.println("에러: " + e);
            return null;
        }
    }
}

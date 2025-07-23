package com.daall.howtoeat.client.food;

import com.daall.howtoeat.client.food.dto.FoodResponseDto;
import com.daall.howtoeat.common.dto.ScrollResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.food.Food;
import info.debatty.java.stringsimilarity.JaroWinkler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    /**
     * 주어진 음식 ID를 기반으로 단일 음식 정보를 조회합니다.
     *
     * - ID에 해당하는 음식 정보가 존재하지 않을 경우 {@link CustomException}을 발생시키며,
     * {@link ErrorType#NOT_FOUND_FOOD} 에러를 응답합니다.
     *
     * @param foodId 조회할 음식의 고유 ID
     * @return {@link FoodResponseDto} 해당 ID에 대한 음식 응답 DTO
     * @throws CustomException 음식 ID에 해당하는 데이터가 존재하지 않는 경우
     */
    public FoodResponseDto getFood(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_FOOD));

        return new FoodResponseDto(food);
    }


    public ScrollResponseDto<FoodResponseDto> searchSimilarFoods(String name, int page, int size){
        String trimmedKeyword = name.replaceAll("\\s+", "");

        // 받아온 음식이름과 공백을 제거한 음식이름으로 Like 검색
        List<Food> candidates = foodRepository.findByNameContainingVariants(name, trimmedKeyword);
        Pageable pageable = PageRequest.of(page, size);
        JaroWinkler jw = new JaroWinkler();

        // 받아온 음식이름과 조회한 값들을 유사도로 정렬, 같은 유사도는 foodTyper
        List<Food> sorted = candidates.stream()
                .sorted(Comparator
                        .comparingDouble((Food f) -> jw.similarity(name, f.getFoodName()))
                        .reversed()
                        .thenComparing(f -> foodTypeOrder(f.getFoodType()))
                )
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), sorted.size());

        List<FoodResponseDto> pageContent = sorted.subList(start, end).stream().map(FoodResponseDto::new).toList();
        Page<FoodResponseDto> foodPage = new PageImpl<>(pageContent, pageable, sorted.size());

        return new ScrollResponseDto<>(pageContent, foodPage.hasNext());
    }

    private int foodTypeOrder(FoodType type) {
        return switch (type) {
            case COOKED-> 0;
            case INGREDIENT -> 1;
            case PROCESSED -> 2;
            case CUSTOM_SHARED -> 3;
            default -> 4;
        };
    }

}

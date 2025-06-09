package com.daall.howtoeat.client.food;

import com.daall.howtoeat.client.food.dto.FoodResponseDto;
import com.daall.howtoeat.common.dto.ScrollResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.food.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    /**
     * 음식 이름에 해당하는 음식 데이터를 페이지네이션하여 조회합니다.
     *
     * - 검색어가 null 또는 공백이면 전체 음식 데이터를 조회합니다.
     * - food_name 컬럼에 name 값이 포함된 데이터를 검색합니다.
     * - 결과는 ScrollResponseDto로 감싸서 반환되며, content와 hasNext를 포함합니다.
     *
     * @param name 검색할 음식 이름 (예: "소고기"). null 또는 공백이면 전체 검색.
     * @param page 조회할 페이지 번호 (0부터 시작).
     * @param size 한 페이지당 조회할 데이터 개수.
     * @return {@link ScrollResponseDto} 음식 응답 DTO 리스트와 다음 페이지 존재 여부를 포함한 객체.
     */
    public ScrollResponseDto<FoodResponseDto> getFoods(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        //검색어가 없을 경우, 빈객체 반환
        if (name == null || name.isBlank()) {
            return new ScrollResponseDto<>(List.of(), false);
        }

        Page<Food> foodPage = foodRepository.findByFoodNameContaining(name, pageable);

        List<FoodResponseDto> dtoList = foodPage.getContent().stream()
                .map(FoodResponseDto::new)
                .toList();

        return new ScrollResponseDto<>(dtoList, foodPage.hasNext());
    }


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
}

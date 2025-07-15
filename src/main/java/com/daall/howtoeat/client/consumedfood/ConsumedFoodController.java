package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodByMealTimeResponseDto;
import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodDetailResponseDto;
import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConsumedFoodController {
    private final ConsumedFoodService consumedFoodService;

    @GetMapping("/consumed-foods")
    public ResponseEntity<ResponseDataDto<List<ConsumedFoodByMealTimeResponseDto>>> getConsumedFoodListByMealTime(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("meal_time") MealTime mealTime
    ) {
        User loginUser = userDetails.getUser();
        List<ConsumedFoodByMealTimeResponseDto> responseDto = consumedFoodService.getConsumedFoodList(loginUser, date, mealTime);

        return ResponseEntity.ok(new ResponseDataDto<>(SuccessType.GET_DAILY_KCAL_SUMMARIES_SUCCESS, responseDto));
    }

    /**
     * 섭취 음식 등록
     * @param requestDtoList - 섭취 음식에 필요한 데이터
     */
    @PostMapping("/consumed-foods")
    public ResponseEntity<ResponseMessageDto> addConsumedFoods(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid List<ConsumedFoodsRequestDto> requestDtoList
    ) {
        User loginUser = userDetails.getUser();
        consumedFoodService.addConsumedFoods(loginUser, requestDtoList);
        SuccessType successType = SuccessType.ADD_CONSUMED_FOOD_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }


    /**
     * 섭취 음식 세부 조회
     * @pathVariable requestDtoList - 섭취 음식에 필요한 데이터
     */
    @GetMapping("/consumed-foods/{consumedFoodId}")
    public ResponseEntity<ResponseDataDto<ConsumedFoodDetailResponseDto>> getConsumedFoodDetailInfo(
            @PathVariable(value = "consumedFoodId") Long consumedFoodId
    ) {
        SuccessType successType = SuccessType.GET_CONSUMED_FOOD_DETAIL_INFO;
        ConsumedFoodDetailResponseDto responseDto = consumedFoodService.getConsumedFoodDetailInfo(consumedFoodId);
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

    /**
     * 섭취 음식 삭제
     *
     * @pathVariable requestDtoList - 섭취 음식에 필요한 데이터
     */
    @DeleteMapping("/consumed-foods/{consumedFoodId}")
    public ResponseEntity<ResponseMessageDto> deleteConsumedFood(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable(value = "consumedFoodId") Long consumedFoodId
    ) {
        User loginUser = userDetails.getUser();
        consumedFoodService.deleteConsumedFood(loginUser, consumedFoodId);
        SuccessType successType = SuccessType.DELETE_CONSUMED_FOOD_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}

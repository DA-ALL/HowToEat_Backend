package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodByMealTimeResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
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
}

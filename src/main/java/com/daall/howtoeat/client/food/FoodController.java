package com.daall.howtoeat.client.food;


import com.daall.howtoeat.client.food.dto.FoodResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.dto.ScrollResponseDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    @GetMapping("/foods")
    public ResponseEntity<ResponseDataDto<ScrollResponseDto<FoodResponseDto>>> getFoods(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
            System.out.println("Controller");

            ScrollResponseDto<FoodResponseDto> result = foodService.getFoods(name, page, size);

            SuccessType successType = SuccessType.GET_DAILY_KCAL_SUMMARIES_SUCCESS;

            return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, result));
    }
}

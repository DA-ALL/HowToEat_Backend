package com.daall.howtoeat.client.food;


import com.daall.howtoeat.client.food.dto.FoodResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.dto.ScrollResponseDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @RequestParam(defaultValue = "20") int size
    ) {
            System.out.println("Controller");

            ScrollResponseDto<FoodResponseDto> result = foodService.searchSimilarFoods(name, page, size);

            SuccessType successType = SuccessType.GET_ALL_FOODS_SUCCESS;

            return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, result));
    }

    @GetMapping("/food/{foodId}")
    public ResponseEntity<ResponseDataDto<FoodResponseDto>> getFood(
            @PathVariable Long foodId
    ) {
            SuccessType successType = SuccessType.GET_FOOD_SUCCESS;
            FoodResponseDto responseDto = foodService.getFood(foodId);
            return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }
}

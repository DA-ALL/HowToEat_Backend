package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.admin.food.dto.AdminFoodRequestDto;
import com.daall.howtoeat.admin.food.dto.FoodShareRequestDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminFoodController {
    private final AdminFoodService adminFoodService;

    @GetMapping("/admin/foods")
    public ResponseEntity<PageResponseDto<AdminFoodResponseDto>> getFoods (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "foodType", required = false) String foodType,
            @RequestParam(value = "recommendation", required = false) String recommendation
    ){
        Page<AdminFoodResponseDto> responseDto = adminFoodService.getFoods(page-1, size, name, orderBy, foodType, recommendation);
        SuccessType successType = SuccessType.GET_ALL_FOODS_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new PageResponseDto<>(successType, responseDto));
    }

    @GetMapping("/admin/foods/{foodId}")
    public ResponseEntity<ResponseDataDto<AdminFoodResponseDto>> getFood(@PathVariable Long foodId){
        AdminFoodResponseDto responseDto = adminFoodService.getFood(foodId);
        SuccessType successType = SuccessType.GET_FOOD_DETAIL_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

    @PostMapping("/admin/foods")
    public ResponseEntity<ResponseMessageDto> createFood(@RequestBody AdminFoodRequestDto requestDto){
        adminFoodService.createFood(requestDto);
        SuccessType successType = SuccessType.CREATE_FOOD_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @PutMapping("/admin/foods/{foodId}")
    public ResponseEntity<ResponseMessageDto> updateFood(
            @PathVariable Long foodId,
            @RequestBody AdminFoodRequestDto requestDto
    ) {
        adminFoodService.updateFood(foodId, requestDto);
        SuccessType successType = SuccessType.UPDATE_FOOD_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @DeleteMapping("/admin/foods/{foodId}")
    public ResponseEntity<ResponseMessageDto> deleteFood(@PathVariable Long foodId) {
        adminFoodService.deleteFood(foodId);
        SuccessType successType = SuccessType.DELETE_FOOD_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @PostMapping("/admin/foods/share")
    public ResponseEntity<ResponseMessageDto> shareFood(@RequestBody FoodShareRequestDto requestDto){
        adminFoodService.shareFood(requestDto);

        SuccessType successType = SuccessType.CREATE_FOOD_SHARE_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}

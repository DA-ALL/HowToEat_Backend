package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}

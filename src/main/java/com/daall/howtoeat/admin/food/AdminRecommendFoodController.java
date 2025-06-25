package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminRecommendFoodController {
    private final RecommendFoodService recommendFoodService;

    @GetMapping("/admin/recommend-foods")
    public ResponseEntity<ResponseDataDto<List<AdminFoodResponseDto>>> getRecommendFoods(
            @RequestParam(value = "sortBy", required = false) String sortBy
    ){
        List<AdminFoodResponseDto> response = recommendFoodService.getRecommendFoods(sortBy);
        SuccessType successType = SuccessType.GET_ALL_RECOMMEND_FOODS_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, response));
    }
}

package com.daall.howtoeat.client.recommendfood;

import com.daall.howtoeat.client.recommendfood.dto.RecommendFoodResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendFoodController {
    private final RecommendFoodService recommendFoodService;

    @GetMapping("/recommend-foods/{date}")
    public ResponseEntity<ResponseDataDto<List<RecommendFoodResponseDto>>> getRecommendFoods(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable LocalDate date
    ) {

        List<RecommendFoodResponseDto> responseDtoList = recommendFoodService.getRecommendFoods(userDetails.getUser(), date);
        SuccessType successType = SuccessType.GET_ALL_RECOMMEND_FOODS_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDtoList));
    }

}

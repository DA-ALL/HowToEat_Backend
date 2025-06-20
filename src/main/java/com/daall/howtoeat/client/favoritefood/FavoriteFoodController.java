package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodAddByConsumedFoodRequestDto;
import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodIdResponseDto;
import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class FavoriteFoodController {
    private final FavoriteFoodService favoriteFoodService;

    @PostMapping("/favorite-foods")
    public ResponseEntity<ResponseDataDto<FavoriteFoodIdResponseDto>> createFavoriteFoodByConsumedFood(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FavoriteFoodAddByConsumedFoodRequestDto requestDto
    ) {
        User loginUser = userDetails.getUser();
        SuccessType successType = SuccessType.CREATE_FAVORITE_SUCCESS;

        FavoriteFoodIdResponseDto responseDto = favoriteFoodService.createFavoriteFoodByConsumedFood(loginUser, requestDto);

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

//    [추후 정리]
//    @PostMapping("/favorite-foods")
//    public ResponseEntity<ResponseMessageDto> createFavoriteFoodByConsumedFood(
//            @AuthenticationPrincipal UserDetailsImpl userDetails,
//            @ModelAttribute FavoriteFoodAddByConsumedFoodRequestDto requestDto,
//            @RequestParam(value = "foodImageFile", required = false) MultipartFile foodImageFile
//    ) {
//        User loginUser = userDetails.getUser();
//        SuccessType successType = SuccessType.CREATE_FAVORITE_SUCCESS;
//
//        favoriteFoodService.createFavoriteFoodByConsumedFood(loginUser, requestDto, foodImageFile);
//        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
//    }


    @GetMapping("/favorite-foods")
    public ResponseEntity<ResponseDataDto<List<FavoriteFoodResponseDto>>> getFavoriteFoodList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User loginUser = userDetails.getUser();
        List<FavoriteFoodResponseDto> responseDtoList = favoriteFoodService.getFavoriteFoodList(loginUser);
        SuccessType successType = SuccessType.GET_FAVORITE_FOOD_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDtoList));
    }

    @DeleteMapping("/favorite-foods/{favoriteFoodId}")
    public ResponseEntity<ResponseMessageDto> deleteFavoriteFood(
            @PathVariable (value = "favoriteFoodId") Long favoriteFoodId
    ) {
        favoriteFoodService.deleteFavoriteFood(favoriteFoodId);
        SuccessType successType = SuccessType.DELETE_FAVORITE_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}

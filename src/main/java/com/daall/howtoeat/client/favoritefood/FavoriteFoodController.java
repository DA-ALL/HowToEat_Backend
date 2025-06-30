package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.client.favoritefood.dto.*;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    /**
     * 즐겨찾기 음식 새로 추가
     *
     * @pathVariable userDetails 현재 로그인한 유저
     * @pathVariable requestDto 즐겨찾기 음식 정보
     */
    @PostMapping("/favorite-foods/new")
    public ResponseEntity<ResponseMessageDto> createFavoriteFood(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FavoriteFoodAddByNewRequestDto requestDto
    ) {
        User loginUser = userDetails.getUser();
        SuccessType successType = SuccessType.CREATE_FAVORITE_SUCCESS;

        favoriteFoodService.createFavoriteFoodByNew(loginUser, requestDto);
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
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


    /**
     * 즐겨찾기 음식 단일 삭제
     *
     * @pathVariable favoriteFoodId - 즐겨찾기 음식 ID
     */
    @DeleteMapping("/favorite-foods/{favoriteFoodId}")
    public ResponseEntity<ResponseMessageDto> deleteFavoriteFood(
            @PathVariable (value = "favoriteFoodId") Long favoriteFoodId
    ) {
        favoriteFoodService.deleteFavoriteFood(favoriteFoodId);
        SuccessType successType = SuccessType.DELETE_FAVORITE_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }


    /**
     * 즐겨찾기 음식 다중 삭제
     *
     * @pathVariable favoriteFoodId - 즐겨찾기 음식 ID
     */
    @DeleteMapping("/favorite-foods")
    public ResponseEntity<ResponseMessageDto> deleteFavoriteFoods(
            @RequestBody @Valid List<FavoriteFoodDeleteRequestDto> requestDtoList
    ) {
        favoriteFoodService.deleteFavoriteFoods(requestDtoList);
        SuccessType successType = SuccessType.DELETE_FAVORITE_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}

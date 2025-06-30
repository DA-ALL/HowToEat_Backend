package com.daall.howtoeat.admin.favoritefood;

import com.daall.howtoeat.admin.favoritefood.dto.AdminFavoriteFoodResponseDto;
import com.daall.howtoeat.admin.favoritefood.dto.AdminFavoriteFoodWithUserResponseDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminFavoriteFoodController {
    private final AdminFavoriteFoodService adminFavoriteFoodService;

    @GetMapping("/admin/favorite-foods")
    public ResponseEntity<PageResponseDto<AdminFavoriteFoodWithUserResponseDto>> getUserRegisteredFoods(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "adminShared", required = false) String adminShared
    ){
        Page<AdminFavoriteFoodWithUserResponseDto> responseDto = adminFavoriteFoodService.getUserRegisteredFoods(page-1, size, name, orderBy, adminShared);
        SuccessType successType = SuccessType.GET_ALL_USER_REGISTERED_FOOD_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new PageResponseDto<>(successType, responseDto));
    }

    @GetMapping("/admin/favorite-foods/{favoriteFoodId}")
    public ResponseEntity<ResponseDataDto<AdminFavoriteFoodResponseDto>> getUserRegisteredFood(@PathVariable Long favoriteFoodId){
        AdminFavoriteFoodResponseDto responseDto = adminFavoriteFoodService.getUserRegisteredFood(favoriteFoodId);
        SuccessType successType = SuccessType.GET_USER_REGISTERED_FOOD_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }
}

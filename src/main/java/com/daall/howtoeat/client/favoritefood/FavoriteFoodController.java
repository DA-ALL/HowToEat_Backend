package com.daall.howtoeat.client.favoritefood;

import com.daall.howtoeat.client.favoritefood.dto.FavoriteFoodAddBySearchRequestDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class FavoriteFoodController {
    private final FavoriteFoodService favoriteFoodService;

    @PostMapping("/favorite-food/search")
    public ResponseEntity<ResponseMessageDto> addFavoriteFoodBySearch(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody FavoriteFoodAddBySearchRequestDto requestDto
    ) {
        User loginUser = userDetails.getUser();

        SuccessType successType = SuccessType.CREATE_FAVORITE_SUCCESS;
        favoriteFoodService.addFavoriteFoodBySearch(loginUser, requestDto);
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}

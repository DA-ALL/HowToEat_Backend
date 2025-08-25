package com.daall.howtoeat.admin.consumedfood;

import com.daall.howtoeat.admin.consumedfood.dto.ConsumedFoodsByDateResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class AdminConsumedFoodController {
    private final AdminConsumedFoodService adminConsumedFoodService;

    /**
     * 날짜별 섭취음식 끼니로 구분하여 조회 - 어드민 - 유저섭취정보 팝업
     *
     * @param userId 조회할 유저 아이디
     * @param date 조회할 날짜
     */
    @GetMapping("/admin/users/{userId}/consumed-foods")
    public ResponseEntity<ResponseDataDto<ConsumedFoodsByDateResponseDto>> getConsumedFoodsByDate(
            @PathVariable Long userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        ConsumedFoodsByDateResponseDto responseDtos = adminConsumedFoodService.getConsumedFoodsByDate(userId, date);
        SuccessType successType = SuccessType.GET_CONSUMED_FOOD_BY_DATE_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDtos));
    }
}

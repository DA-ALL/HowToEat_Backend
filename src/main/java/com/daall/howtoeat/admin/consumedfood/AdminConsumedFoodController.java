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
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminConsumedFoodController {
    private final AdminConsumedFoodService adminConsumedFoodService;

    @GetMapping("/admin/users/{userId}/consumed-foods")
    public ResponseEntity<ResponseDataDto<List<ConsumedFoodsByDateResponseDto>>> getConsumedFoodsByDate(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {

        List<ConsumedFoodsByDateResponseDto> responseDtos = adminConsumedFoodService.getConsumedFoodsByDate(userId, date);
        SuccessType successType = SuccessType.GET_CONSUMED_FOOD_BY_DATE_SUCCESS;


        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDtos));
    }


}

package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.admin.gym.dto.GymRequestDto;
import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
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
@RequestMapping("/admin/gyms")
public class GymController {
    private final GymService gymService;

    /**
     * 헬스장 전체 조회
     * @param page 페이지
     * @param size 페이지 사이즈
     * @return 헬스장 리스트
     */
    @GetMapping
    private ResponseEntity<PageResponseDto<GymResponseDto>> getGyms (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "name", required = false) String name
    ){
        Page<GymResponseDto> gyms = gymService.getGyms(page - 1, size, name);
        SuccessType successType = SuccessType.GET_ALL_GYMS_SUCCESS;
        PageResponseDto<GymResponseDto> responseDto = new PageResponseDto<>(successType, gyms);

        return ResponseEntity.status(successType.getHttpStatus()).body(responseDto);
    }

    /**
     * 헬스장 단일 조회
     * @param gymId 헬스장 아이디
     * @return 해당 헬스장 데이터
     */
    @GetMapping("/{gymId}")
    private ResponseEntity<ResponseDataDto<GymResponseDto>> getGym (@PathVariable Long gymId){
        GymResponseDto gym = gymService.getGym(gymId);
        SuccessType successType = SuccessType.GET_GYM_DETAIL_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, gym));
    }

    /**
     * 헬스장 추가
     * @param requestDto 추가할 헬스장 데이터
     * @return 성공 메시지
     */
    @PostMapping
    private ResponseEntity<ResponseMessageDto> createGym(@RequestBody GymRequestDto requestDto){
        gymService.createGym(requestDto);
        SuccessType successType = SuccessType.CREATE_GYM_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    /**
     * 헬스장 수정
     * @param gymId 헬스장 아이디
     * @param requestDto 수정 정보
     * @return 성공 메시지
     */
    @PutMapping("/{gymId}")
    private ResponseEntity<ResponseMessageDto> updateGym(@PathVariable Long gymId ,@RequestBody GymRequestDto requestDto){
        gymService.updateGym(gymId, requestDto);
        SuccessType successType = SuccessType.UPDATE_GYM_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    /**
     * 헬스장 삭제
     * @param gymId 헬스장 아이디
     * @return 성공 메시지
     */
    @DeleteMapping("/{gymId}")
    private ResponseEntity<ResponseMessageDto> deleteGym(@PathVariable Long gymId){
        gymService.deleteGym(gymId);
        SuccessType successType = SuccessType.DELETE_GYM_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}

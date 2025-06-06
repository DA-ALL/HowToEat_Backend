package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.admin.trainer.dto.TrainerResponseDto;
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
@RequestMapping("/admin/trainers")
public class TrainerController {
    private final TrainerService trainerService;
    /**
     * 트레이너 전체 조회
     * @param page 페이지
     * @param size 페이지 사이즈
     * @param name 트레이너 이름
     * @param gym 헬스장 id
     * @return 트레이너 리스트
     */
    @GetMapping
    private ResponseEntity<PageResponseDto<TrainerResponseDto>> getTrainers (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gym", required = false) String gym
    ){
        Page<TrainerResponseDto> gyms = trainerService.getTrainers(page - 1, size, name, gym);
        SuccessType successType = SuccessType.GET_ALL_TRAINERS_SUCCESS;
        PageResponseDto<TrainerResponseDto> responseDto = new PageResponseDto<>(successType, gyms);

        return ResponseEntity.status(successType.getHttpStatus()).body(responseDto);
    }

    /**
     * 트레이너 단일 조회
     * @param gymId 트레이너 아이디
     * @return 해당 트레이너 데이터
     */
//    @GetMapping("/{gymId}")
//    private ResponseEntity<ResponseDataDto<GymResponseDto>> getTrainer (@PathVariable Long gymId){
//        GymResponseDto gym = trainerService.getTrainer(gymId);
//        SuccessType successType = SuccessType.GET_TRAINER_DETAIL_SUCCESS;
//
//        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, gym));
//    }
//
//    /**
//     * 트레이너 추가
//     * @param requestDto 트레이너 데이터
//     * @return 성공 메시지
//     */
//    @PostMapping
//    private ResponseEntity<ResponseMessageDto> createTrainer(@RequestBody GymRequestDto requestDto){
//        trainerService.createTrainer(requestDto);
//        SuccessType successType = SuccessType.CREATE_TRAINER_SUCCESS;
//
//        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
//    }
//
//    /**
//     * 트레이너 수정
//     * @param gymId 트레이너 아이디
//     * @param requestDto 수정 정보
//     * @return 성공 메시지
//     */
//    @PutMapping("/{gymId}")
//    private ResponseEntity<ResponseMessageDto> updateTrainer(@PathVariable Long gymId ,@RequestBody GymRequestDto requestDto){
//        trainerService.updateTrainer(gymId, requestDto);
//        SuccessType successType = SuccessType.UPDATE_TRAINER_SUCCESS;
//
//        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
//    }
//
//    /**
//     * 트레이너 삭제
//     * @param gymId 트레이너 아이디
//     * @return 성공 메시지
//     */
//    @DeleteMapping("/{gymId}")
//    private ResponseEntity<ResponseMessageDto> deleteTrainer(@PathVariable Long gymId){
//        trainerService.deleteTrainer(gymId);
//        SuccessType successType = SuccessType.DELETE_TRAINER_SUCCESS;
//
//        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
//    }
}

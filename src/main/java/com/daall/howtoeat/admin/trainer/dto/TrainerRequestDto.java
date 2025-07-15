package com.daall.howtoeat.admin.trainer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TrainerRequestDto {
    @NotBlank(message = "트레이너 이름을 입력하세요.")
    private String name;
    @NotBlank(message = "헬스장이 선택되지 않았습니다.")
    private Long gymId;
    private String imageUrl;
}

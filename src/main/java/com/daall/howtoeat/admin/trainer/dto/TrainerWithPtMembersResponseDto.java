package com.daall.howtoeat.admin.trainer.dto;

import com.daall.howtoeat.admin.ptmember.dto.PtMemberUserResponseDto;
import com.daall.howtoeat.admin.user.dto.AdminUserResponseDto;
import com.daall.howtoeat.domain.pt.Trainer;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class TrainerWithPtMembersResponseDto {
    private final Long trainerId;
    private final String name;
    private final String imageUrl;
    private final Page<PtMemberUserResponseDto> ptMembers;

    public TrainerWithPtMembersResponseDto(Trainer trainer, Page<PtMemberUserResponseDto> ptMembers){
        this.trainerId = trainer.getId();
        this.name = trainer.getName();
        this.imageUrl = trainer.getImageUrl();
        this.ptMembers = ptMembers;
    }
}


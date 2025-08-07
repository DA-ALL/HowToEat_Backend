package com.daall.howtoeat.admin.ptmember.dto;

import com.daall.howtoeat.admin.user.dto.AdminUserResponseDto;
import com.daall.howtoeat.domain.pt.PtMember;
import lombok.Getter;

@Getter
public class PtMemberUserResponseDto {
    private final Long ptMemberId;
    private final AdminUserResponseDto user;

    public PtMemberUserResponseDto(PtMember ptMember) {
        this.ptMemberId = ptMember.getId();
        this.user = new AdminUserResponseDto(ptMember.getUser(), 0L);
    }
}
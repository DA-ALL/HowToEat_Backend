package com.daall.howtoeat.client.notice;

import com.daall.howtoeat.admin.notice.NoticeRepository;
import com.daall.howtoeat.client.notice.dto.UserNoticeDetailResponseDto;
import com.daall.howtoeat.client.notice.dto.UserNoticeResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.notice.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserNoticeService {
    private final NoticeRepository noticeRepository;

    /**
     * 공지사항 전체 조회
     */
    public List<UserNoticeResponseDto> getNotices(){

        ArrayList<UserNoticeResponseDto> responseDtos = new ArrayList<>();

        List<Notice> noticeList = noticeRepository.findAllByOrderByModifiedAtDesc();

        for(Notice notice : noticeList) {
            responseDtos.add(new UserNoticeResponseDto(notice));
        }

        return responseDtos;
    }


    /**
     * 공지사항 상세 조회
     */
    public UserNoticeDetailResponseDto getNoticeInfo(Long noticeId){
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_NOTICE));
        return new UserNoticeDetailResponseDto(notice);
    }

}

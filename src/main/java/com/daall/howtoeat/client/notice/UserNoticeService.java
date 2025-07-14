package com.daall.howtoeat.client.notice;

import com.daall.howtoeat.admin.notice.NoticeRepository;
import com.daall.howtoeat.client.notice.dto.NoticeResponseDto;
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
    public List<NoticeResponseDto> getNotices(){

        ArrayList<NoticeResponseDto> responseDtos = new ArrayList<>();

        List<Notice> noticeList = noticeRepository.findAllByOrderByModifiedAtDesc();

        for(Notice notice : noticeList) {
            responseDtos.add(new NoticeResponseDto(notice));
        }

        return responseDtos;
    }

}

package com.daall.howtoeat.admin.notice;

import com.daall.howtoeat.domain.notice.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findByTitleContaining(String title, Pageable pageable);
    List<Notice> findAllByOrderByModifiedAtDesc();
}

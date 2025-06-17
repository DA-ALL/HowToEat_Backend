package com.daall.howtoeat.admin.ptmember;

import com.daall.howtoeat.domain.pt.PtMember;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PtMemberRepositoryQuery {
    Page<PtMember> findUsersByTrainerId(Long trainerId, Pageable pageable);
}

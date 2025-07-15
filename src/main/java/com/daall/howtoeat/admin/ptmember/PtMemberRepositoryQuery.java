package com.daall.howtoeat.admin.ptmember;

import com.daall.howtoeat.domain.pt.PtMember;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PtMemberRepositoryQuery {
    Page<PtMember> findUsersByTrainerId(Long trainerId, Pageable pageable);

    Map<Long, Long> countPtMembersByTrainerIds(List<Long> trainerIds);
}

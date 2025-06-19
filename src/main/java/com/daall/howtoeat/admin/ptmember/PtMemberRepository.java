package com.daall.howtoeat.admin.ptmember;

import com.daall.howtoeat.domain.pt.PtMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PtMemberRepository extends JpaRepository<PtMember, Long>, PtMemberRepositoryQuery {
    boolean existsByTrainerIdAndUserId(Long trainerId, Long userId);

    Long countByTrainerId(Long trainerId);
}

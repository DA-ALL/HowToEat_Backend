package com.daall.howtoeat.admin.ptmember;

import com.daall.howtoeat.admin.ptmember.dto.PtMemberRequestDto;
import com.daall.howtoeat.admin.ptmember.dto.PtMemberUserResponseDto;
import com.daall.howtoeat.admin.trainer.TrainerRepository;
import com.daall.howtoeat.admin.trainer.dto.TrainerWithPtMembersResponseDto;
import com.daall.howtoeat.admin.user.AdminUserService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.pt.PtMember;
import com.daall.howtoeat.domain.pt.Trainer;
import com.daall.howtoeat.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PtMemberService {
    private final PtMemberRepository ptMemberRepository;
    private final AdminUserService adminUserService;
    private final TrainerRepository trainerRepository;

    public void createPtMember(PtMemberRequestDto requestDto) {
        Trainer trainer = trainerRepository.findById(requestDto.getTrainerId()).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );
        User user = adminUserService.findUserById(requestDto.getUserId());

        if(ptMemberRepository.existsByTrainerIdAndUserId(trainer.getId(), user.getId())){
            throw new CustomException(ErrorType.ALREADY_EXISTS_PT_MEMBER);
        };

        PtMember ptMember = new PtMember(trainer, user);
        ptMemberRepository.save(ptMember);
    }

    public TrainerWithPtMembersResponseDto getTrainerWithPtMembers(Long trainerId, int page, int size) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );
        Pageable pageable = PageRequest.of(page, size);

        Page<PtMember> ptMembers = ptMemberRepository.findUsersByTrainerId(trainerId, pageable);
        Page<PtMemberUserResponseDto> ptMemberUserResponseDtos = ptMembers.map(PtMemberUserResponseDto::new);

        return new TrainerWithPtMembersResponseDto(trainer, ptMemberUserResponseDtos);
    }

    @Transactional
    public void deletePtMember(Long ptMemberId) {
        PtMember ptMember = ptMemberRepository.findById(ptMemberId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_PT_MEMBER)
        );

        ptMemberRepository.delete(ptMember);
    }

    public Long getPtMemberCountByTrainerId(Long trainerId){
        return ptMemberRepository.countByTrainerId(trainerId);
    }

    public Map<Long, Long> getPtMemberCountsByTrainerIds(List<Long> trainerIds) {
        return ptMemberRepository.countPtMembersByTrainerIds(trainerIds);
    }

    @Transactional
    public void deletePtMemberByTrainer(Trainer trainer) {
        ptMemberRepository.deleteAllByTrainer(trainer);
    }
}

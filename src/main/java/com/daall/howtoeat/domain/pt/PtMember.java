package com.daall.howtoeat.domain.pt;

import com.daall.howtoeat.admin.ptmember.dto.PtMemberRequestDto;
import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "pt_members")
public class PtMember extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PtMember(Trainer trainer, User user) {
        this.trainer = trainer;
        this.user= user;
    }
}

package com.daall.howtoeat.domain.user;

import com.daall.howtoeat.admin.account.dto.AdminAccountRequestDto;
import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.SignupProvider;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    // 생년월일은 날짜 연산 및 타입 안정성을 위해 String 대신 LocalDate 사용
    // (예: 나이 계산, 날짜 비교 등에서 유리하고 DB에서도 날짜 타입으로 저장됨)
    @Column(nullable = false)
    private LocalDate birth;

    @Column(nullable = false)
    private boolean isNextGym;

    @Column
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus userStatus;

    //SSO 회원가입 타입 (ex: 네이버, 카카오, 애플)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignupProvider signup_provider;

    @Column(columnDefinition = "Text")
    private String refreshToken;

    public User(SignupRequestDto requestDto) {
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.gender = requestDto.getGender();
        this.birth = requestDto.getBirthday();
        this.isNextGym = requestDto.isNextGym();
        this.profileImageUrl = requestDto.getProfileImageUrl();
        this.userRole = UserRole.USER;
        this.userStatus = UserStatus.ACTIVATE;
        this.signup_provider = requestDto.getSignupProvider();
    }

    public User(AdminAccountRequestDto requestDto, String password){
        this.email = requestDto.getAccountId();
        this.password = password;

        this.name = "admin";
        this.gender = Gender.MALE;
        this.birth = LocalDate.now();
        this.isNextGym = true;
        this.userRole = UserRole.ADMIN;
        this.userStatus = UserStatus.ACTIVATE;
        this.signup_provider = SignupProvider.ADMIN;
    }

    public void updateAdminAccount(AdminAccountRequestDto requestDto, String password){
        this.email = requestDto.getAccountId();
        this.password = password;
    }

    public void saveRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken.substring(7);
    }
}

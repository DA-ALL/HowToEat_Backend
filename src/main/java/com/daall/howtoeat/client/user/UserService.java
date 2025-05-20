package com.daall.howtoeat.client.user;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserTargetService userTargetService;



    public User signup(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = new User(requestDto);

        User savedUser = userRepository.save(user);

        userTargetService.createTarget(requestDto, savedUser);

        //추후 userStatsService로 분리
        UserStat userStats = new UserStat(savedUser, requestDto);
        return user;
    }

    public void test() {
        System.out.println("test입니당~");
    }

    public void updateRefreshToken(User user, String refreshToken) {
        user.saveRefreshToken(refreshToken);
        userRepository.save(user);
    }
}

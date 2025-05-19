package com.daall.howtoeat.client.user;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStats;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    public void createUser(SignupRequestDto requestDto){
//        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
//        User user = new User(requestDto, encodedPassword);
//
//        userRepository.save(user);
//    }

    public void signup(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = new User(requestDto);

        User savedUser = userRepository.save(user);

        //추후 userStatsService로 분리
        new UserStats(savedUser, requestDto);
    }

    public void test() {
        System.out.println("test입니당~");
    }
}

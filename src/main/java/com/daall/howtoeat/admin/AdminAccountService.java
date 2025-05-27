package com.daall.howtoeat.admin;

import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.client.user.UserService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createAdminAccount(User user, @Valid AdminAccountRequestDto requestDto) {
        // User Role은 시큐리티에서 확인
        // 이미 존재하는 아이디인지
        if(userRepository.existsByEmail(requestDto.getAccountId())){
            throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User newUser = new User(requestDto, encodedPassword);

        userRepository.save(newUser);
    }
}

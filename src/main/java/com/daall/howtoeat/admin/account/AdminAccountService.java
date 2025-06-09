package com.daall.howtoeat.admin.account;

import com.daall.howtoeat.admin.account.dto.AdminAccountRequestDto;
import com.daall.howtoeat.admin.account.dto.AdminAccountResponseDto;
import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.SignupProvider;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createAdminAccount(AdminAccountRequestDto requestDto) {
        // 이미 존재하는 아이디인지
        if(userRepository.existsByEmail(requestDto.getAccountId())){
            throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User newUser = new User(requestDto, encodedPassword);

        userRepository.save(newUser);
    }

    public Page<AdminAccountResponseDto> getAdminAccounts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> adminAccounts = userRepository.findAllByUserRole(UserRole.ADMIN, pageable);

        return adminAccounts.map(AdminAccountResponseDto::new);
    }

    public AdminAccountResponseDto getAdminAccount(Long accountId) {
        User user = userRepository.findById(accountId).orElseThrow(
                ()-> new CustomException(ErrorType.NOT_FOUND_USER)
        );

        return new AdminAccountResponseDto(user);
    }

    @Transactional
    public void updateAdminAccount(Long accountId, AdminAccountRequestDto requestDto) {
        User user = userRepository.findById(accountId).orElseThrow(
                ()-> new CustomException(ErrorType.NOT_FOUND_USER)
        );

        if(!user.getUserRole().equals(UserRole.ADMIN) || !user.getSignup_provider().equals(SignupProvider.ADMIN)) {
            throw new CustomException(ErrorType.NOT_ADMIN_ACCOUNT);
        }

        if(userRepository.existsByEmailAndIdNot(requestDto.getAccountId(), accountId)){
            throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);
        }

        String encoded = passwordEncoder.encode(requestDto.getPassword());
        user.updateAdminAccount(requestDto, encoded);
    }

    @Transactional
    public void deleteAdminAccount(Long accountId) {
        User user = userRepository.findById(accountId).orElseThrow(
                ()-> new CustomException(ErrorType.NOT_FOUND_USER)
        );

        if(!user.getUserRole().equals(UserRole.ADMIN) || !user.getSignup_provider().equals(SignupProvider.ADMIN)) {
            throw new CustomException(ErrorType.NOT_ADMIN_ACCOUNT);
        }

        userRepository.delete(user);
    }
}

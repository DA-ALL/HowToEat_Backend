package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
//@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
//    @PostMapping("/signup")
//    public ResponseEntity<String> createUser(@Valid @RequestBody SignupRequestDto requestDto) {
//        userService.createUser(requestDto);
//        return ResponseEntity.ok("회원가입 완료");
//    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupNaver(@RequestBody @Valid SignupRequestDto dto) {
        System.out.println("안녕하세요안녕하세요안녕하세요안녕하세요");
        userService.signup(dto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginNaver(@RequestBody @Valid SignupRequestDto dto) {
        userService.signup(dto);
        return ResponseEntity.ok("회원가입 완료");
    }

    @GetMapping("/force-login")
    public void forceLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        // Spring Security context 로그아웃 처리
        request.getSession().invalidate();

        // 네이버 인증 페이지로 강제 이동
        response.sendRedirect("/oauth2/authorization/naver");
    }

    @GetMapping("/test")
    public ResponseEntity<String> getAccessToken(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok("테스트 완료");
    }
}

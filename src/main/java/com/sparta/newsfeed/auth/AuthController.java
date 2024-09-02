package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService loginService;

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {

        try {
            LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);

            if(loginResponseDto != null) {
                return ResponseEntity.ok().body(loginResponseDto);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during login");
        }
    }

    /*// 로그아웃, BlackList
    public ResponseEntity<?> logout() {

        try {

        } catch (Exception e) {
            return ResponseEntity.status()
        }
    }*/
}

package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService loginService;


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        try {
            LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);

            if(loginResponseDto != null) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("Authorization", loginResponseDto.getToken());
                return ResponseEntity.ok().headers(httpHeaders).body(loginResponseDto);
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

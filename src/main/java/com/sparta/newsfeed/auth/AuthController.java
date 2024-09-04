package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import com.sparta.newsfeed.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService loginService;
    private final UserService userService;
    private final AuthService authService;
    private final UserRepository userRepository;


/*    // 프로필 생성
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto requestDto) {

        // 사용자 서비스 호출 생성
        UserResponseDto userResponseDto = authService.register(requestDto);
        // 성공적 생성 -> 201 상태 코드로 생성된 사용자를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);

    }*/


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

    // 로그아웃
    public ResponseEntity<?> logout() {

    }

}

package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
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

import static com.sparta.newsfeed.config.exception.ErrorCode.BAD_REQUEST;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService loginService;
    private final UserService userService;
    private final AuthService authService;
    private final UserRepository userRepository;


    // 프로필 생성
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto requestDto) {

        try {
            // 사용자 서비스 호출 생성
            UserResponseDto userResponseDto = authService.register(requestDto);
            // 성공적 생성 -> 201 상태 코드로 생성된 사용자를 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);

        } catch (CustomException e){
            throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {

        try {
            LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", loginResponseDto.getToken());
            return ResponseEntity.ok().headers(httpHeaders).body(loginResponseDto);

        } catch (CustomException e){
            throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


}

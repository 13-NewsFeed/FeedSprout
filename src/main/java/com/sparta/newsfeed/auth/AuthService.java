package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import com.sparta.newsfeed.auth.util.JwtUtil;
import com.sparta.newsfeed.config.UserPasswordEncoder;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;

    // 프로필 생성
    @Transactional
    public UserResponseDto register(UserRequestDto requestDto) {
        if (requestDto.getEmail() == null && requestDto.getUsername() == null) {
            throw new IllegalArgumentException("Email과 nickname은 필수입니다.");
        }
        // 이메일 중복 체크.
        if(userRepository.existsByEmail(requestDto.getEmail())){
            throw new IllegalArgumentException("이미 사용하고 있는 Email 입니다.");
        }

        User user = new User(requestDto);

        String password = userPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);

    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        User user = userRepository.findByEmail(email).get();
        if(userPasswordEncoder.matches(password, user.getPassword())) {

            String token = jwtUtil.generateAccessToken(user);
            LoginResponseDto loginResponseDto = new LoginResponseDto(token, email);

            return loginResponseDto;

        } else {
            return null;
        }
    }
}



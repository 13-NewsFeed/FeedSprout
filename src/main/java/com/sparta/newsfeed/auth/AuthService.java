package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import com.sparta.newsfeed.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String email = jwtUtil.getUserEmailFromToken(loginRequestDto.getEmail());

        User user = userRepository.findByEmail(email).get();
        if(user != null && userPasswordEncoder.matches(password, user.getPassword())) {

            String token = jwtUtil.generateAccessToken(user);
            LoginResponseDto loginResponseDto = new LoginResponseDto(token, email);

            return loginResponseDto;

        } else {
            return null;
        }
    }

}

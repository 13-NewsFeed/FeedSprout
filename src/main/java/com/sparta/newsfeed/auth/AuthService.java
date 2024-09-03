package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.config.UserPasswordEncoder;
import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import com.sparta.newsfeed.auth.util.JwtUtil;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final UserPasswordEncoder userPasswordEncoder;
    private final UserRepository userRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByEmail(email).get();
        if(password.equals(user.getPassword())) {

            String token = jwtUtil.generateAccessToken(user);
            LoginResponseDto loginResponseDto = new LoginResponseDto(token, email);

            return loginResponseDto;

        } else {
            return null;
        }
    }

}

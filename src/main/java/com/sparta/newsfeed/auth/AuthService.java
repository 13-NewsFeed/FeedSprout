package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.auth.dto.LoginRequestDto;
import com.sparta.newsfeed.auth.dto.LoginResponseDto;
import com.sparta.newsfeed.auth.util.JwtUtil;
import com.sparta.newsfeed.config.UserPasswordEncoder;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.Image;
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
    private UserPasswordEncoder userPasswordEncoder;


    // 프로필 생성
    @Transactional
    public UserResponseDto register(UserRequestDto requestDto) {
                if (requestDto.getEmail() == null || requestDto.getUsername() == null) {
                    throw new CustomException(ErrorCode.BAD_REQUEST);
                }
                // 이메일 중복 체크.
                if (userRepository.existsByEmail(requestDto.getEmail())) {
                    throw new CustomException(ErrorCode.CONFLICT);
                }

                User user = new User(requestDto);

                Image image = new Image(requestDto.getProfileImageUrl(),"PROFILE", user, null);
                user.setProfileImage(image);

                String password = userPasswordEncoder.encode(user.getPassword());
                user.setPassword(password);
                User savedUser = userRepository.save(user);

                return new UserResponseDto(savedUser);

    }


    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        if (loginRequestDto.getEmail() == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByEmail(email).get();
        if (userPasswordEncoder.matches(password, user.getPassword())) {

            String token = jwtUtil.generateAccessToken(user);
            LoginResponseDto loginResponseDto = new LoginResponseDto(token, email);

            return loginResponseDto;

        } else {
            throw new CustomException(ErrorCode.CONFLICT);
        }
    }
}



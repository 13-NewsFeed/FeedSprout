package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.auth.util.PasswordUtils;
import com.sparta.newsfeed.config.UserPasswordEncoder;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserPasswordEncoder userPasswordEncoder;


    // 회원 탈퇴
    @Transactional
    public UserResponseDto deleteProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        userRepository.delete(user);
        return new UserResponseDto(user);
    }

    // 프로필 조회
    public UserResponseDto getProfileById(Long userId) {

        //유저 조회
        User user = userRepository.findById(userId).orElseThrow(()
                -> new CustomException(ErrorCode.NOT_FOUND));
        return new UserResponseDto(user);
    }


    // 프로필 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {

        // 유저 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        // 현재 비밀번호 확인
        if (!userPasswordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        // 이름 업데이트
        if (requestDto.getUsername() != null) {
            if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }
            user.setUsername(requestDto.getUsername());
        }


        // 이메일 업데이트
        if (requestDto.getEmail() != null) {
            if (userRepository.existsByEmail(requestDto.getEmail())) {
                throw new CustomException(ErrorCode.BAD_REQUEST);
            }
            user.setEmail(requestDto.getEmail());
        }


        // 비밀번호 업데이트
        // 새로운 비빌번호와 새로운 비밀번호 재확인
        if (!requestDto.getNewPassword().equals(requestDto.getNewConfirmPassword())) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        // 새로운 비밀번호 형식 검증
        PasswordUtils.checkPasswordFormat(requestDto.getNewPassword());

        // 현재 비밀번호와 새로운 비밀번호가 같은 경우
        if (user.getPassword().equals(requestDto.getNewPassword())) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        // 새로운 비밀번호 인코딩
        String encodeNewPassword = userPasswordEncoder.encode(requestDto.getNewPassword());
        user.setPassword(encodeNewPassword);


        // 사용자 저장
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }
}

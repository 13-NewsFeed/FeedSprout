package com.sparta.newsfeed.user.service;

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


    // 프로필 조회 (readOnly)
    public UserResponseDto getProfileById(Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id +" : 유저의 id를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }


    // 프로필 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(id + " : 유저의 id를 찾을 수 없습니다."));

        // 정보 업데이트
        if(requestDto.getNickname() != null) {
            user.setNickname(requestDto.getNickname());
        }
        if(requestDto.getEmail() != null) {
            user.setEmail(requestDto.getEmail());
        }
        if(requestDto.getPassword() != null) {
            user.setPassword(requestDto.getPassword());
        }

        // 사용자 저장
        User updateUser = userRepository.save(user);

        return new UserResponseDto(updateUser);

    }


}

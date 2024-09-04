package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.user.dto.FollowResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.service.UserFeatureService;
import com.sparta.newsfeed.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserFeatureService userFeatureService;
    public Map<String, ErrorCode> errorMap = new HashMap<>();

    public UserController(UserService userService, UserFeatureService userFeatureService){
        this.userService = userService;
        this.userFeatureService = userFeatureService;
    }



    // 프로필 조회
    @GetMapping("/profiles/{id}")
    public ResponseEntity<UserResponseDto> getProfileById(@PathVariable Long id) {

        UserResponseDto userResponseDto = userService.getProfileById(id);
        // 성공적 조회 : 200 상태 코드로 조회된 사용자 정보를 반환
        return ResponseEntity.ok(userResponseDto);

    }



    // 프로필 수정
    @PutMapping("/profiles/{id}")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long id
            , @RequestBody UserRequestDto requestDto){

        try {
            UserResponseDto updatedUser = userService.updateUser(id, requestDto);
            // 성공적 수정 : 200 상태 코드로 조회된 사용자 정보를 반환
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException ex) {
            // 잘못된 요청 데이터 : 404 코드로 에러 메세지 반환 (문자열로 반환)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception ex) {
            // 서버 오류 : 500 코드로 에러 메세지 반환 (문자열로 반환)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 팔로우 걸기
    @PostMapping("/profiles/{id}/follows")
    public ResponseEntity<?> followUser(@RequestParam(value = "from") Long from,
                                        @RequestParam(value = "to") Long to) {
        FollowResponseDto followResponseDto = userFeatureService.followUser(from, to);

        return ResponseEntity.ok().body(followResponseDto);
    }
}

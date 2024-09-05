package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.user.dto.FollowResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.service.UserFeatureService;
import com.sparta.newsfeed.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserFeatureService userFeatureService;
    public Map<String, ErrorCode> errorMap = new HashMap<>();

    public UserController(UserService userService, UserFeatureService userFeatureService) {
        this.userService = userService;
        this.userFeatureService = userFeatureService;
    }


    // 프로필 조회
    @GetMapping("/profiles/{id}")
    public ResponseEntity<UserResponseDto> getProfileById(@PathVariable Long id) {
        try {
            UserResponseDto userResponseDto = userService.getProfileById(id);
            return ResponseEntity.ok(userResponseDto);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 회원 탈퇴
    @DeleteMapping("/profiles/{userId}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long userId) {
        try {
            userService.deleteProfile(userId);
            return ResponseEntity.ok().body("Withdraw Success");
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        }
    }


    // 프로필 수정
    @PutMapping("/profiles/{id}")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long id
            , @RequestBody UserRequestDto requestDto) {

        try {
            UserResponseDto updatedUser = userService.updateUser(id, requestDto);
            return ResponseEntity.ok(updatedUser);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.METHOD_NOT_ALLOWED);
        }
    }


    // 팔로우 걸기
    @PostMapping("/profiles/{id}/follows")
    public ResponseEntity<FollowResponseDto> followUser(@RequestParam(value = "from") Long from,
                                                        @RequestParam(value = "to") Long to) {
        try {
            FollowResponseDto followResponseDto = userFeatureService.followUser(from, to);
            return ResponseEntity.ok().body(followResponseDto);

        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CONFLICT);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }


    // 팔로워 삭제
    @DeleteMapping("/delete/{id}/follows")
    public ResponseEntity<String> deleteFollower(@PathVariable Long followerId, @PathVariable Long followeeId) {
        try {
            userFeatureService.deleteFollower(followerId, followeeId);
            return ResponseEntity.ok("팔로워가 성공적으로 삭제되었습니다.");

        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CONFLICT);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }


    // 나한테 팔로우 건 애들 가져오기
    @GetMapping("/{id}/followers")
    public ResponseEntity<List<String>> getFollowers(@PathVariable Long id) {
        try {
            List<String> followers = userFeatureService.getFollowers(id);
            return ResponseEntity.ok(followers);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CONFLICT);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }


    // 내가 팔로우 건 애들 가져오기
    @GetMapping("/{id}/followees")
    public ResponseEntity<List<String>> getFollowees(@PathVariable Long id) {
        try {
            List<String> followees = userFeatureService.getFollowees(id);
            return ResponseEntity.ok(followees);
        } catch (CustomException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.CONFLICT);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
    }

        // 팔로우 대기목록 확인
        @GetMapping("/{id}/waiting-followers")
        public ResponseEntity<List<String>> getWaitingFollowers (@PathVariable Long id){
            try {
                List<String> waitingFollowers = userFeatureService.getWaitingFollowers(id);
                return ResponseEntity.ok(waitingFollowers);
            } catch (CustomException e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

            } catch (Exception e) {
                throw new CustomException(ErrorCode.FORBIDDEN);
            }
        }


        // 팔로우 승낙 혹은 거절하기
        @PostMapping("/update")
        public ResponseEntity<Void> updateFollowState (
                @RequestParam Long followerId, @RequestParam Long followeeId, @RequestParam String state){
            try {
                userFeatureService.updateFollowState(followerId, followeeId, state);
                return ResponseEntity.ok().build();
            } catch (CustomException e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.FORBIDDEN);
            }
        }
    }

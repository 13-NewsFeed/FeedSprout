package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.config.Auth;
import com.sparta.newsfeed.config.exception.CustomException;
import com.sparta.newsfeed.config.exception.ErrorCode;
import com.sparta.newsfeed.follow.dto.FollowResponseDto;
import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.service.UserFeatureService;
import com.sparta.newsfeed.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    @GetMapping("/profiles/{userid}")
    public ResponseEntity<UserResponseDto> getProfileById(@PathVariable Long userid) throws CustomException {
        UserResponseDto userResponseDto = userService.getProfileById(userid);
        // 성공적 조회 : 200 상태 코드로 조회된 사용자 정보를 반환
        return ResponseEntity.ok(userResponseDto);
    }

    // 회원 탈퇴
    @DeleteMapping("/profiles/{userId}")
    public ResponseEntity<String> deleteProfile(@PathVariable Long userId) {
        try {
            userService.deleteProfile(userId);
            return ResponseEntity.ok().body("Withdraw Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during login");
        }
    }


    // 프로필 수정
    @PutMapping("/profiles/{userid}")
    public ResponseEntity<UserResponseDto> updateProfile(@PathVariable Long userid,
                                                         @RequestBody UserRequestDto requestDto){

        UserResponseDto updatedUser = userService.updateUser(userid, requestDto);
        // 성공적 수정 : 200 상태 코드로 조회된 사용자 정보를 반환
        return ResponseEntity.ok(updatedUser);

    }

    // 팔로우 걸기
    @PostMapping("/follows/{followeeId}")
    public ResponseEntity<FollowResponseDto> followUser(AuthUser user,
                                                        @PathVariable(name = "followeeId") Long followeeId) {
        FollowResponseDto followResponseDto = userFeatureService.followUser(user.getId(), followeeId);

        return ResponseEntity.ok().body(followResponseDto);
    }

    // 팔로워 삭제
    @DeleteMapping("/follows/{id}")
    public ResponseEntity<String> deleteFollower(AuthUser authUser, @PathVariable Long id) {
        try {
            userFeatureService.deleteFollower(authUser.getId(), id);
            return ResponseEntity.ok("팔로워가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 나한테 팔로우 건 애들, 내가 팔로우 건 애들 가져오기
    @GetMapping("/follows/followingList")
    public ResponseEntity<List<String>> getFollowers(AuthUser authUser) {
        List<String> followers = userFeatureService.getFollowers(authUser.getId());
        List<String> followees = userFeatureService.getFollowees(authUser.getId());
        List<String> follows = Stream.concat(followers.stream(), followees.stream()).toList();
        return ResponseEntity.ok(follows);
    }


    // 팔로우 대기목록 확인
    @GetMapping("/follows/waitingFollowers")
    public ResponseEntity<List<String>> getWaitingFollowers(AuthUser authUser){
        List<String> waitingFollowers = userFeatureService.getWaitingFollowers(authUser.getId());
        return ResponseEntity.ok(waitingFollowers);
    }

    // 팔로우 승낙 혹은 거절하기
    @PostMapping("/follows/waitingFollowers/{followerId}")
    public ResponseEntity<Void> updateFollowState(
            AuthUser authUser, @PathVariable Long followerId, @RequestParam String state) {
        userFeatureService.updateFollowState(followerId, authUser.getId(), state);
        return ResponseEntity.ok().build();
    }
}

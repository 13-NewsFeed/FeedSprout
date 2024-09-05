package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.auth.dto.AuthUser;
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

        try {
            UserResponseDto updatedUser = userService.updateUser(userid, requestDto);
            // 성공적 수정 : 200 상태 코드로 조회된 사용자 정보를 반환
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException ex) {
            // 잘못된 요청 데이터 : 404 코드로 에러 메세지 반환 (문자열로 반환)
            throw new CustomException(ErrorCode.NOT_FOUND);
        } catch (Exception ex) {
            // 서버 오류 : 500 코드로 에러 메세지 반환 (문자열로 반환)
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 팔로우 걸기
    @PostMapping("/profiles/{userId}/follows/{followeeId}")
    public ResponseEntity<FollowResponseDto> followUser(AuthUser user,
                                                        @PathVariable(name = "followeeId") Long followeeId) {
        FollowResponseDto followResponseDto = userFeatureService.followUser(user.getId(), followeeId);

        return ResponseEntity.ok().body(followResponseDto);
    }

    // 팔로워 삭제
    @DeleteMapping("/profiles/{userId}/follows/{id}")
    public ResponseEntity<String> deleteFollower(@RequestParam Long followerId, @RequestParam Long followeeId) {
        try {
            userFeatureService.deleteFollower(followerId, followeeId);
            return ResponseEntity.ok("팔로워가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 나한테 팔로우 건 애들, 내가 팔로우 건 애들 가져오기
    @GetMapping("/profiles/{userId}/follows")
    public ResponseEntity<List<String>> getFollowers(@PathVariable Long userId){
        List<String> followers = userFeatureService.getFollowers(userId);
        List<String> followees = userFeatureService.getFollowees(userId);
        List<String> follows = Stream.concat(followers.stream(), followees.stream()).toList();
        return ResponseEntity.ok(follows);
    }


    // 팔로우 대기목록 확인
    @GetMapping("/profiles/{userId}/follows/waitingFollowers")
    public ResponseEntity<List<String>> getWaitingFollowers(@PathVariable Long userId){
        List<String> waitingFollowers = userFeatureService.getWaitingFollowers(userId);
        return ResponseEntity.ok(waitingFollowers);
    }

    // 팔로우 승낙 혹은 거절하기
    @PostMapping("/profiles/{userId}/follows/waitingFollowers/{followerId}")
    public ResponseEntity<Void> updateFollowState(
            @PathVariable Long userId, @RequestParam Long followerId, @RequestParam String state) {
        userFeatureService.updateFollowState(followerId, userId, state);
        return ResponseEntity.ok().build();
    }
}

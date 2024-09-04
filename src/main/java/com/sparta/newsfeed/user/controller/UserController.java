package com.sparta.newsfeed.user.controller;

import com.sparta.newsfeed.auth.dto.AuthUser;
import com.sparta.newsfeed.config.exception.CustomException;
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
import java.util.List;
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
    public ResponseEntity<UserResponseDto> getProfileById(@PathVariable Long id) throws CustomException {
        UserResponseDto userResponseDto = userService.getProfileById(id);
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
    @PostMapping("/profiles/follows/{followeeId}")
    public ResponseEntity<FollowResponseDto> followUser(AuthUser user,
                                                        @PathVariable(name = "followeeId") Long followeeId) {
        FollowResponseDto followResponseDto = userFeatureService.followUser(user.getId(), followeeId);

        return ResponseEntity.ok().body(followResponseDto);
    }

    // 팔로워 삭제
    @DeleteMapping("/delete/{id}/follows")
    public ResponseEntity<String> deleteFollower(@RequestParam Long followerId, @RequestParam Long followeeId) {
        try {
            userFeatureService.deleteFollower(followerId, followeeId);
            return ResponseEntity.ok("팔로워가 성공적으로 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // 나한테 팔로우 건 애들 가져오기
    @GetMapping("/{id}/followers")
    public ResponseEntity<List<String>> getFollowers(@PathVariable Long id){
        List<String> followers = userFeatureService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    // 내가 팔로우 건 애들 가져오기
    @GetMapping("/{id}/followees")
    public ResponseEntity<List<String>> getFollowees(@PathVariable Long id) {
        List<String> followees = userFeatureService.getFollowees(id);
        return ResponseEntity.ok(followees);
    }

    // 팔로우 대기목록 확인
    @GetMapping("/{id}/waiting-followers")
    public ResponseEntity<List<String>> getWaitingFollowers(@PathVariable Long id){
        List<String> waitingFollowers = userFeatureService.getWaitingFollowers(id);
        return ResponseEntity.ok(waitingFollowers);
    }

    // 팔로우 승낙 혹은 거절하기
    @PostMapping("/profiles/follows")
    public ResponseEntity<Void> updateFollowState(
            @RequestParam Long followerId, @RequestParam Long followeeId, @RequestParam String state) {
        userFeatureService.updateFollowState(followerId, followeeId, state);
        return ResponseEntity.ok().build();
    }
}

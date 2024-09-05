package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.service.UserService;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AuthService authService;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 테스트")
    void register() {

        for(int i=0 ; i < 100 ; i++) {
            String formattedNumber = String.format("%04d", i);
            String email = "test"+formattedNumber+"@example.com";
            String username = "test"+formattedNumber;
            UserRequestDto userRequestDto = new UserRequestDto();
            userRequestDto.setEmail(email);
            userRequestDto.setPassword("0000");
            userRequestDto.setUsername(username);

            UserResponseDto userResponseDto = authService.register(userRequestDto);
            assertNotNull(userRequestDto);
            assertEquals(userResponseDto.getUsername(), username);
        }
    }
}

// 전체 통합 테스트
// 서버를 실제로 돌려가며 테스트 진행
// 단점: 어떤 부분에서 에러가 발생했는지 트래킹이 어려움
// 단점: 속도가 많이 느림

// 단위 테스트, 계층별 테스트
// UserService, Controller 만 테스트 진행
// "빠르게" 내가 작성한 코드에 대한 확신을 갖고싶어서 진행하는 것
// 어렵고 미리 사전에 상황에 대한 세팅이 되어야 함
// 메서드 단위로 테스트 진행은 가능
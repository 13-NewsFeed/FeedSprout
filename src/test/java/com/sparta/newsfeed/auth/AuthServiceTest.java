package com.sparta.newsfeed.auth;

import com.sparta.newsfeed.user.dto.UserRequestDto;
import com.sparta.newsfeed.user.dto.UserResponseDto;
import com.sparta.newsfeed.user.entity.User;
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

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

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


package com.sparta.newsfeed.auth.util;

import com.sparta.newsfeed.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // 유저에 Role이 있으면 적용하겠음
    // public static final String AUTHORIZATION_KEY = "auth";

    private final long ACCESS_TOKEN_TIME = 15 * 60 * 1000; // 15분
    private final long REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000; // 1일

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static final Logger logger = LoggerFactory.getLogger("JWT 로그");



    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }



    // Token 생성 메서드
    public String generateAccessToken(User user) {
        Long now = System.currentTimeMillis();
        String email = user.getEmail();

        return Jwts.builder()
                .setSubject(String.valueOf(email)) // 사용자 식별자값(ID)
                .claim("userId", String.valueOf(user.getId()))
                .setExpiration(new Date(now + ACCESS_TOKEN_TIME)) // 만료 시간
                .setIssuedAt(new Date(now)) // 생성 시간
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact(); // 각 구성요소를 결합하여 JWT 문자열 생성

    }



    public String generateRefreshToken(User user) {
        Long now = System.currentTimeMillis();
        Long userId = user.getId();
        String email = user.getEmail();

        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 사용자 식별자값(ID)
                .claim("email", email)
                .setExpiration(new Date(now + REFRESH_TOKEN_TIME)) // 만료 시간
                .setIssuedAt(new Date(now)) // 생성 시간
                .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                .compact(); // 각 구성요소를 결합하여 JWT 문자열 생성
    }



    // Header에서 Token 가져오기
    public String getJwtFromHeader(HttpServletRequest httpServletRequest) {
        // getHeader(String name)를 사용해 특정 Header 값을 가져올 수 있음
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        // 가져온 값이 존재하는지, 그리고 그 값이 Bearer로 시작하는지 확인
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            // Bearer 부분만 잘라서 Token 반환
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null; // 유효한 토큰이 없으면 null 반환
    }



    // 토큰 검증하기
    public boolean validateToken(String token) {
        try {
            // JWT를 파싱하기 위한 빌더를 생성, JWT 서명 검증용 키 설정을 한 뒤 빌드
            // 그후에 parseClaimsJws()를 사용해서 JWT를 파싱하고 서명을 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            return true;

        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }



    // Claims에 담긴 유저 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}

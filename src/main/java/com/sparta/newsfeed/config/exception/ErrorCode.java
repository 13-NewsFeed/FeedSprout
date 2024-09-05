package com.sparta.newsfeed.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 BAD_REQUEST : 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "not a valid request"),

    // 401 UNAUTHORIZED : 인증되지 않음, 인증이 필요함
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),

    // 403 FORBIDDEN : 요청한 자원에 대한 접근 권한 없음
    FORBIDDEN(HttpStatus.FORBIDDEN, "Access is forbidden."),

    // 404 NOT_FOUND : 리소스를 찾을 수 없음
    NOT_FOUND(HttpStatus.NOT_FOUND, "it is not found"),

    // 405 METHOD_NOT_ALLOWED : 허용되지 않은 메서드
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "not allowed method"),

    // 409 CONFLICT : 요청이 서버와 충돌함
    CONFLICT(HttpStatus.CONFLICT, "Conflict occurred."),

    // 500 INTERNAL_SERVER_ERROR : 서버에서 처리할 수 없는 오류 발생
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");


    private final HttpStatus status;
    private final String message;
}

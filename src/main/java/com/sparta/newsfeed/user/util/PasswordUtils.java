package com.sparta.newsfeed.user.util;

public class PasswordUtils {

    public static void checkPasswordFormat(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8글자 이상의 비밀번호를 입력하세요.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("비밀번호는 하나 이상의 대문자가 포함되어야 합니다.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("비밀번호는 하나 이상의 숫자가 포함되어야 합니다.");
        }
    }
}

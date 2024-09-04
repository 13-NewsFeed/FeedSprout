package com.sparta.newsfeed.like.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class LikeResponseDto {
    private String msg; // 메시지
    private int code; // 응답코드

    @Builder
    public LikeResponseDto(String msg, int code){
        this.msg = msg;
        this.code = code;
    }

}
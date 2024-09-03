package com.sparta.newsfeed.user.dto;

import com.sparta.newsfeed.user.entity.Follow;
import lombok.Getter;

@Getter
public class FollowResponseDto {

    private Long followId;
    private String followerName;
    private String followeeName;

    public FollowResponseDto(Follow follow) {
        this.followId = follow.getId();
        this.followerName = follow.getFollower().getNickname();
        this.followeeName = follow.getFollowee().getNickname();
    }
}

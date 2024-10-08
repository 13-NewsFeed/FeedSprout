package com.sparta.newsfeed.follow.dto;

import com.sparta.newsfeed.follow.entity.Follow;
import lombok.Getter;

@Getter
public class FollowResponseDto {

    private Long followId;
    private String followerName;
    private String followeeName;

    public FollowResponseDto(Follow follow) {
        this.followId = follow.getId();
        this.followerName = follow.getFollower().getUsername();
        this.followeeName = follow.getFollowee().getUsername();
    }
}

package com.sparta.newsfeed.like.controller;

import com.sparta.newsfeed.like.dto.StatusResult;
import com.sparta.newsfeed.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class LikeController {
    private final LikeService likeService;

    //게시글 좋아요
    //요청을 @PathVariable 와 @AuthenticationPrincipal 로만 처리할 수 있음.(RequestDto 필요 x )
    @PostMapping("/{post-id}/likes")
    public StatusResult LikePost(@PathVariable Long id){

        return likeService.likePost(id, customFiled.getUser());   //<- User 변경.
    }

    // 게시글 좋아요 취소
    @DeleteMapping("/{post-id}/likes")
    public StatusResult deleteLikePost(@PathVariable Long id){
        return likeService.deleteLikePost(id, customFiled.getUser());
    }

    // 댓글 좋아요
    @PostMapping("/comments/{comment-id}/likes")
    public StatusResult likeComment(@PathVariable Long id){
        return likeService.likeComment(id, customFiled.getUser());
    }

    // 댓글 좋아요 취소
    @DeleteMapping("/comments/{comment-id}/likes")
    public StatusResult deleteLikeComment(@PathVariable Long id{
        return likeService.deleteLikeComment(id, customFiled.getUser());
    }
}

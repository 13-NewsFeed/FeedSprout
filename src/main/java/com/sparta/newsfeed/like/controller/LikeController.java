package com.sparta.newsfeed.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{}")
public class LikeController {


    @PostMapping("/likes/{userid}")
    public ResponseEntity<?> like(@PathVariable Long userid) {

        return ResponseEntity.status(404).body("");
    }
}

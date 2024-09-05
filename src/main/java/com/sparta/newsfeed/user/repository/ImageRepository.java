package com.sparta.newsfeed.user.repository;

import com.sparta.newsfeed.user.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

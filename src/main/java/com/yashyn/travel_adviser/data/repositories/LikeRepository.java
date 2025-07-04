package com.yashyn.travel_adviser.data.repositories;

import com.yashyn.travel_adviser.data.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {
    List<Like> findByPostId(UUID postId);
    void deleteByUserIdAndPostId(UUID userId, UUID postId);
}

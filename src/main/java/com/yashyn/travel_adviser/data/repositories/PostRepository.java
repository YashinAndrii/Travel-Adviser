package com.yashyn.travel_adviser.data.repositories;

import com.yashyn.travel_adviser.data.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByUserUsername(String username);
    List<Post> findByUserId(UUID userId);
}

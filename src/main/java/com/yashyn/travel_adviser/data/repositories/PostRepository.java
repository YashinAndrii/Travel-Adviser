package com.yashyn.travel_adviser.data.repositories;

import com.yashyn.travel_adviser.data.entities.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByUserUsername(String username);

/*    @EntityGraph(value = "Post.countriesCitiesPhotos", type = EntityGraph.EntityGraphType.LOAD)
    List<Post> findAll();*/

    List<Post> findByUserId(UUID userId);
}

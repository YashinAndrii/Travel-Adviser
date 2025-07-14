package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.data.dto.CreatePostDto;
import com.yashyn.travel_adviser.data.dto.PostDto;
import com.yashyn.travel_adviser.services.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid CreatePostDto postDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID id, @RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(id, postDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/advice")
    public ResponseEntity<String> generateAdvice(@RequestBody @Valid CreatePostDto postDto) {
        return ResponseEntity.ok(postService.generateTravelAdvice(postDto));
    }
}

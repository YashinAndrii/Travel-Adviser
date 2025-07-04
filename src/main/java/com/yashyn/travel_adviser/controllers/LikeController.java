package com.yashyn.travel_adviser.controllers;

import com.yashyn.travel_adviser.data.dto.LikeDto;
import com.yashyn.travel_adviser.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeDto> likePost(@RequestBody LikeDto likeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(likeService.addLike(likeDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@RequestBody LikeDto likeDto) {
        likeService.removeLike(likeDto.getUserId(), likeDto.getPostId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getLikesByPost(@PathVariable UUID postId) {
        return ResponseEntity.ok(likeService.getLikesByPostId(postId));
    }
}

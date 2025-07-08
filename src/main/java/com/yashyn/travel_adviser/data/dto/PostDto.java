package com.yashyn.travel_adviser.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yashyn.travel_adviser.data.entities.Comment;
import com.yashyn.travel_adviser.data.entities.Like;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class PostDto {
    private UUID id;
    private UUID userId;

    private String username;
    private String avatarUrl;

    private Set<String> photos;
    private Set<String> countries;
    private Set<String> cities;
    private String dates;
    private String type;
    private String budget;

    @JsonProperty("isPlanned")
    private boolean planned;
    private LocalDateTime createdAt;

    @JsonProperty("likes")
    private Set<LikeDto> likes;
    private Set<CommentDto> comments;
}

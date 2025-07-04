package com.yashyn.travel_adviser.data.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CommentDto {
    private UUID id;
    private UUID postId;
    private UUID userId;

    private String username;
    private String avatarUrl;

    private String content;
    private LocalDateTime createdAt;
}

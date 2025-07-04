package com.yashyn.travel_adviser.data.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PostDto {
    private UUID id;
    private UUID userId;

    private String username;
    private String avatarUrl;

    private List<String> photos;
    private List<String> countries;
    private List<String> cities;
    private String dates;
    private String type;
    private String budget;
    private boolean planned;
    private LocalDateTime createdAt;

    private int likeCount;
    private int commentCount;
}

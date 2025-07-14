package com.yashyn.travel_adviser.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private BigDecimal budget;

    @JsonProperty("isPlanned")
    private boolean planned;
    private LocalDateTime createdAt;

    @JsonProperty("likes")
    private Set<LikeDto> likes;
    private Set<CommentDto> comments;

    private String plannedNote;
    private String description;
    private String travelAdvice;
}

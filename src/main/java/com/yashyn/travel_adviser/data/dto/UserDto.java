package com.yashyn.travel_adviser.data.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String displayName;
    private String avatarUrl;
    private String bio;
    private LocalDateTime createdAt;
}

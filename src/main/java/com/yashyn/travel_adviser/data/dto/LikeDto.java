package com.yashyn.travel_adviser.data.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class LikeDto {
    private UUID id;
    private UUID userId;
    private UUID postId;
}
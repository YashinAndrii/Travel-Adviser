package com.yashyn.travel_adviser.data.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TravelAdviceDto {
    private UUID adviceId;
    private PostDto post;
    private boolean isTransportIncluded;
    private int amountOfPeople;
    private String plan;
}

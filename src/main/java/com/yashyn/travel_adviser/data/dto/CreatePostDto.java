package com.yashyn.travel_adviser.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
public class CreatePostDto {
    private UUID userId;
    private Set<String> photos;
    private Set<String> countries;
    private Set<String> cities;
    private String description;
    private String plannedNote;
    @JsonProperty("isPlanned")
    private boolean planned;
    private String budget;
    private String dates;
    private String type;
}

package com.yashyn.travel_adviser.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class TravelPlanResponseDto {
    private String summary;
    private List<DayPlanDto> days;
}

package com.yashyn.travel_adviser.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class DayPlanDto {
    private int day;
    private String city;
    private List<String> activities;
}

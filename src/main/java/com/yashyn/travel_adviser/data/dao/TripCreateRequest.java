package com.yashyn.travel_adviser.data.dao;

import com.yashyn.travel_adviser.data.entities.TripType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TripCreateRequest {
    private List<String> countries;
    private List<String> cities;
    private TripType type;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private boolean includeTransport;
    private boolean hasChildren;
    private boolean completed;
    private String additionalInfo;
}


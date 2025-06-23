package com.yashyn.travel_adviser.data.dto;

import com.yashyn.travel_adviser.data.entities.TripType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record TripDto(
        Long id,
        List<String> countries,
        List<String> cities,
        TripType type,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal budget,
        boolean includeTransport,
        boolean hasChildren,
        boolean completed,
        String imagePath,
        String advice) {}


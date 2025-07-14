package com.yashyn.travel_adviser.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yashyn.travel_adviser.services.validation.OnlyLetters;
import com.yashyn.travel_adviser.services.validation.UuidSet;
import com.yashyn.travel_adviser.services.validation.ValidDateRange;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@ValidDateRange
public class CreatePostDto {
    private UUID userId;

    @NotEmpty
    @UuidSet
    private Set<String> photos;

    @NotEmpty
    @OnlyLetters
    private Set<String> countries;

    @NotEmpty
    @OnlyLetters
    private Set<String> cities;

    private String description;

    private String plannedNote;

    private String travelAdvice;

    @JsonProperty("isPlanned")
    private boolean planned;

    @DecimalMin(value = "0.0", inclusive = false, message = "Budget must be greater than 0")
    private BigDecimal budget;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    private String type;
}

package com.yashyn.travel_adviser.services.validation;

import com.yashyn.travel_adviser.data.dto.CreatePostDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, CreatePostDto> {
    @Override
    public boolean isValid(CreatePostDto dto, ConstraintValidatorContext context) {
        if (dto.getStartDate() == null || dto.getEndDate() == null) return true;
        return dto.getStartDate().isBefore(dto.getEndDate());
    }
}


package com.yashyn.travel_adviser.services.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class OnlyLettersValidator implements ConstraintValidator<OnlyLetters, Set<String>> {
    @Override
    public boolean isValid(Set<String> values, ConstraintValidatorContext context) {
        if (values == null) return true;
        return values.stream().allMatch(v -> v != null && v.matches("^[A-Za-zÀ-ÿ\\s\\-]+$"));
    }
}


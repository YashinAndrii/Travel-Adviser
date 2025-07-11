package com.yashyn.travel_adviser.services.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class UuidSetValidator implements ConstraintValidator<UuidSet, Set<String>> {
    private static final Pattern PHOTO_URL_PATTERN = Pattern.compile(
            "^https://travelgram-media\\.s3\\.eu-north-1\\.amazonaws\\.com/photo/[0-9a-fA-F\\-]{36}/[0-9a-fA-F\\-]{36}$"
    );

    @Override
    public boolean isValid(Set<String> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) return false;

        return values.stream().allMatch(v ->
                v != null && PHOTO_URL_PATTERN.matcher(v).matches()
        );
    }
}
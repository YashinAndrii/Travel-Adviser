package com.yashyn.travel_adviser.services.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OnlyLettersValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyLetters {
    String message() default "Only letters are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
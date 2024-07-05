package com.tumafare.com.tumafare.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidNumber, String> {

    private static final String PHONE_NUMBER_PATTERN = "^254[^+].*$";

    @Override
    public void initialize(ValidNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return false;
        }
        phoneNumber = phoneNumber.startsWith("+") ? phoneNumber.substring(1) : phoneNumber;
        return phoneNumber.matches(PHONE_NUMBER_PATTERN);
    }
}

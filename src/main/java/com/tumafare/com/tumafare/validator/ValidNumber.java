package com.tumafare.com.tumafare.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ValidNumber {
    String message() default "phone_number must start with 254.. remove + sign or 07xx and set in the right format e.g 25472000000";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}

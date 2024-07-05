package com.tumafare.com.tumafare.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ValidPassword {

    String message() default "Password must be more than 12 characters, include numbers, special characters, upper case, and lower case letters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

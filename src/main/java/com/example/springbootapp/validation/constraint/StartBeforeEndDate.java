package com.example.springbootapp.validation.constraint;

import com.example.springbootapp.validation.constraintvalidator.StartBeforeEndDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = StartBeforeEndDateValidator.class)
@Target(ElementType.TYPE) // Applied at the class level
//@Target({ElementType.FIELD}) // for class field
@Retention(RetentionPolicy.RUNTIME)
public @interface StartBeforeEndDate {

    String message() default "Start date must be before end date.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.example.springbootapp.validation.constraintvalidator;

import com.example.springbootapp.validation.constraint.StartBeforeEndDate;
import com.example.springbootapp.web.controller.employee.EmployeeRequestJson;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StartBeforeEndDateValidator implements ConstraintValidator<StartBeforeEndDate, EmployeeRequestJson> {

    @Override
    public boolean isValid(EmployeeRequestJson value, ConstraintValidatorContext context) {
        if (value.getStartDate() == null || value.getEndDate() == null) {
            return true; // Let @NotNull handle null cases if needed
        }

        return value.getStartDate().isBefore(value.getEndDate());
    }
}

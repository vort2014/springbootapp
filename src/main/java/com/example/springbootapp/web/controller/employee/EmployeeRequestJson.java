package com.example.springbootapp.web.controller.employee;

import com.example.springbootapp.validation.constraint.StartBeforeEndDate;
import com.example.springbootapp.validation.group.OnCreate;
import com.example.springbootapp.validation.group.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@StartBeforeEndDate(message = "startDate should be before endDate", groups = {OnCreate.class, OnUpdate.class})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeRequestJson {

    @Null(message = "id must not be present", groups = OnCreate.class)
    @NotBlank(message = "id should not be blank", groups = OnUpdate.class)
    String id;
    @NotBlank(message = "firstName is mandatory", groups = {OnCreate.class, OnUpdate.class})
    String firstName;
    @NotBlank(message = "lastName is mandatory", groups = {OnCreate.class, OnUpdate.class})
    String lastName;
    @NotBlank(message = "email is mandatory", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "email must be valid", groups = {OnCreate.class, OnUpdate.class})
    String email;
    @NotNull(message = "salary is mandatory", groups = {OnCreate.class, OnUpdate.class})
    @Positive(message = "salary must be positive", groups = {OnCreate.class, OnUpdate.class})
    BigDecimal salary;
    @NotNull(message = "startDate is mandatory", groups = {OnCreate.class, OnUpdate.class})
    @Past(message = "startDate must be in the past", groups = {OnCreate.class, OnUpdate.class})
    Instant startDate;
    @NotNull(message = "endDate is mandatory", groups = {OnCreate.class, OnUpdate.class})
    @Past(message = "endDate must be in the past", groups = {OnCreate.class, OnUpdate.class})
    Instant endDate;
}

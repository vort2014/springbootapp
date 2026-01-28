package com.example.springbootapp.web.controller.employee;

import com.example.springbootapp.dao.employee.EmployeeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponseJson {

    String id;
    String firstName;
    String lastName;
    String email;
    BigDecimal salary;
    Instant startDate;
    Instant endDate;
    String companyName;

    public static EmployeeResponseJson from(EmployeeEntity e) {
        return EmployeeResponseJson.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .email(e.getEmail())
                .salary(e.getSalary())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .companyName(e.getCompany() == null ? null : e.getCompany().getName())
                .build();
    }
}

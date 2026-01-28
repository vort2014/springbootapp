package com.example.springbootapp.dao.employee;

import com.example.springbootapp.ApplicationIT;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeRepositoryIT extends ApplicationIT {

    @Autowired
    protected EmployeeRepository employeeRepository;

    @Test
    @Disabled
    void insertEmployee() {

        // given
        var startDate = Instant.now().minus(1, ChronoUnit.DAYS);
        var endDate = Instant.now();
        var employee = EmployeeEntity.builder()
                .id(UUID.randomUUID().toString())
                .firstName("firstName")
                .lastName("LastName")
                .email("example@example.com")
                .salary(BigDecimal.valueOf(10.10D))
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // when
        employeeRepository.save(employee);

        // then
        var employees = employeeRepository.findAll();
        assertThat(employees).hasSize(1);
        var actual = employees.iterator().next();
        assertThat(actual.getFirstName()).isEqualTo("firstName");
        assertThat(actual.getLastName()).isEqualTo("LastName");
        assertThat(actual.getEmail()).isEqualTo("example@example.com");
//        assertThat(actual.getSalary()).isEqualTo(BigDecimal.valueOf(10.10D));
//        assertThat(actual.getStartDate()).isEqualTo(startDate);
//        assertThat(actual.getEndDate()).isEqualTo(endDate);
    }
}
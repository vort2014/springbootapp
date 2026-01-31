package com.example.springbootapp.validation.constraintvalidator;

import com.example.springbootapp.web.controller.employee.EmployeeRequestJson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StartBeforeEndDateValidatorTest {

    @InjectMocks
    StartBeforeEndDateValidator validator;

    @Test
    @DisplayName("startDate and endDate are null")
    void isValid() {

        // given
        var employee = EmployeeRequestJson.builder().build();

        // when
        var actual = validator.isValid(employee, null);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("startDate is after endDate")
    void isValid2() {

        // given
        var employee = EmployeeRequestJson.builder()
                .startDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .endDate(Instant.now())
                .build();

        // when
        var actual = validator.isValid(employee, null);

        // then
        assertThat(actual).isFalse();
    }
}
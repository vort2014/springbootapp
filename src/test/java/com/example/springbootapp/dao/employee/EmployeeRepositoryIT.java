package com.example.springbootapp.dao.employee;

import com.example.springbootapp.ApplicationIT;
import com.example.springbootapp.dao.company.CompanyEntity;
import com.example.springbootapp.dao.company.CompanyRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeRepositoryIT extends ApplicationIT {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void insertEmployee() {

        // given
        var startDate = Instant.now()
                .minus(1, ChronoUnit.DAYS)
                .truncatedTo(ChronoUnit.MILLIS);
        var endDate = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        var employee = EmployeeEntity.builder()
                .firstName("firstName")
                .lastName("LastName")
                .email("example@example.com")
                .salary(BigDecimal.valueOf(10.123D))
                .startDate(startDate)
                .endDate(endDate)
                .build();

        // when
        employeeRepository.save(employee);

        // then
        var employees = employeeRepository.findAll();
        assertThat(employees).hasSize(1);
        var actual = employees.getFirst();
        assertThat(actual.getFirstName()).isEqualTo("firstName");
        assertThat(actual.getLastName()).isEqualTo("LastName");
        assertThat(actual.getEmail()).isEqualTo("example@example.com");
        assertThat(actual.getSalary()).isEqualTo(BigDecimal.valueOf(10.12D));
        assertThat(actual.getStartDate()).isEqualTo(startDate);
        assertThat(actual.getEndDate()).isEqualTo(endDate);
    }

    @Test
    @Disabled
    void findAll() {

        // given
        var companies = new LinkedList<CompanyEntity>();
        for (int i = 0; i < 2; i++) {
            companies.add(CompanyEntity.builder()
                    .name("companyName" + i)
                    .build());
        }
        var companyEntities = companyRepository.saveAll(companies);
        var employees = new ArrayList<EmployeeEntity>();
        for  (int i = 0; i < 10; i++) {
            employees.add(EmployeeEntity.builder()
                    .firstName("firstName")
                    .lastName("LastName")
                    .email("example@example.com")
                    .salary(BigDecimal.valueOf(10.123D))
                    .startDate(Instant.now().minus(2, ChronoUnit.HOURS))
                    .endDate(Instant.now().minus(1, ChronoUnit.HOURS))
                    .company(companyEntities.get(i % 2))
                    .build());
        }
        employeeRepository.saveAll(employees);

        // when


        var q = employeeRepository.findAll();
        var e = q.iterator().next();
        var company = e.getCompany();
        var employees2 = company.getEmployees();
        var ee = employees2.iterator().next();
        System.out.println(ee);
    }
}
package com.example.springbootapp.dao.employee;

import com.example.springbootapp.dao.company.CompanyEntity;
import com.example.springbootapp.web.controller.employee.EmployeeRequestJson;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "employee")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEntity {

    @Id
    String id;
    String firstName;
    String lastName;
    String email;
    BigDecimal salary;
    Instant startDate;
    Instant endDate;
    @ManyToOne
    @JoinColumn(name = "company_id")
    CompanyEntity company;
//    @Embedded
//    private CreatedLastModified createdLastModified;

    public static EmployeeEntity from(EmployeeRequestJson json) {
        return EmployeeEntity.builder()
                .id(UUID.randomUUID().toString())
                .firstName(json.getFirstName())
                .lastName(json.getLastName())
                .email(json.getEmail())
                .salary(json.getSalary())
                .startDate(json.getStartDate())
                .endDate(json.getEndDate())
                .build();
    }

    public EmployeeEntity copyPropertiesFrom(EmployeeRequestJson json) {
        setFirstName(json.getFirstName());
        setLastName(json.getLastName());
        setEmail(json.getEmail());
        setSalary(json.getSalary());
        setStartDate(json.getStartDate());
        setEndDate(json.getEndDate());
        return this;
    }
}

package com.example.springbootapp.dao.employee;

import com.example.springbootapp.dao.company.CompanyEntity;
import com.example.springbootapp.web.controller.employee.EmployeeRequestJson;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "employee")
@Entity
@Data
@ToString(exclude = "company")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    String id;
    String firstName;
    String lastName;
    String email;
    @Column(scale = 2)
    BigDecimal salary;
    @Column(secondPrecision = 3)
    Instant startDate;
    @Column(secondPrecision = 3)
    Instant endDate;
    @ManyToOne
    @JoinColumn(name = "company_id")
    CompanyEntity company;

    public static EmployeeEntity from(EmployeeRequestJson json) {
        return EmployeeEntity.builder()
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

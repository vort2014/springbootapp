package com.example.springbootapp.dao.company;

import com.example.springbootapp.dao.CreatedLastModified;
import com.example.springbootapp.dao.employee.EmployeeEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@Table(name = "company")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyEntity {

    @Id
    String id;
    String name;
    @OneToMany
    @JoinColumn(name = "company_id")
    Collection<EmployeeEntity> employees;
//    @Embedded
//    private CreatedLastModified createdLastModified;
}

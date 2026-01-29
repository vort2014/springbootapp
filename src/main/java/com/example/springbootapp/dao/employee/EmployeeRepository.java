package com.example.springbootapp.dao.employee;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;

@Order(10)
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {
}

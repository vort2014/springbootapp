package com.example.springbootapp.dao.employee;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Order(10)
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {

//    @Query("""
//        SELECT employee
//        FROM EmployeeEntity employee JOIN FETCH employee.company
//    """)
//    List<EmployeeEntity> findAll();
}

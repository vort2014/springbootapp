package com.example.springbootapp.dao.employee;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

@Order(10)
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, String> {

    // because default deleteAllById() doesn't work with fetch = FetchType.EAGER
    @Modifying
    @Query("""
        DELETE EmployeeEntity e
        WHERE e.id IN (:ids)
    """)
    void deleteAllById(Collection<String> ids);
}
